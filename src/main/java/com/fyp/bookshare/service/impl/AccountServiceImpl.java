package com.fyp.bookshare.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.entity.dto.UserLoginDTO;
import com.fyp.bookshare.entity.vo.request.ConfirmResetVO;
import com.fyp.bookshare.entity.vo.request.EmailRegisterVO;
import com.fyp.bookshare.entity.vo.request.EmailResetVO;
import com.fyp.bookshare.mapper.AccountMapper;
import com.fyp.bookshare.mapper.admin.UsersMapper;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.AccountService;
import com.fyp.bookshare.service.admin.IRolesService;
import com.fyp.bookshare.service.admin.IUserPivotRolesService;
import com.fyp.bookshare.utils.Const;
import com.fyp.bookshare.utils.FlowUtils;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 账户信息处理相关服务
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Users> implements AccountService {

    //验证邮件发送冷却时间限制，秒为单位
    @Value("${spring.web.verify.mail-limit}")
    int verifyLimit;

    @Resource
    AmqpTemplate rabbitTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    FlowUtils flow;

    @Resource
    IUserPivotRolesService userPivotRolesService;

    /**
     * 从数据库中通过用户名或邮箱查找用户详细信息
     *
     * @param username 用户名，实际上是email
     * @return 用户详细信息
     * @throws UsernameNotFoundException 如果用户未找到则抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginDTO userLoginDTO = this.getUserByEmail(username);

        if (userLoginDTO == null)
            throw new UsernameNotFoundException("Incorrect email!");

        return User
                .withUsername(username)
                .password(userLoginDTO.getPassword())
                .roles(String.valueOf(userLoginDTO.getRoles()))
                .build();
    }

    /**
     * Generate the registration verification code and store it in Redis,
     * and submit the email sending request to the message queue waiting to be sent.
     *
     * @param type    either register or reset
     * @param email   email address
     * @param address request IP address
     * @return The operation result, null means normal, otherwise it is the cause of the error
     */
    public String registerEmailVerifyCode(String type, String email, String address) {
        // if the type is register and the email is already registered
        if (type.equals("register") && this.existsAccountByEmail(email)) {
            return "This email address has been registered";
        }

        synchronized (address.intern()) {
            if (!this.verifyLimit(address))
                return "Frequent requests, please try again later";

            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            Map<String, Object> data = Map.of(
                    "type", type,
                    "email", email,
                    "code", code);

            rabbitTemplate.convertAndSend(Const.MQ_MAIL, data);

            stringRedisTemplate
                    .opsForValue()
                    .set(Const.VERIFY_EMAIL_DATA + email,
                            String.valueOf(code),
                            3, TimeUnit.MINUTES);

            return null;
        }
    }

    /**
     * When registering an account with an email verification code,
     * need to check whether the verification code is correct
     * and whether the email address is duplicated
     *
     * @param info registration information
     * @return The operation result, null means normal, otherwise it is the cause of the error
     */
    @Transactional(rollbackFor = Exception.class)
    public String registerEmailAccount(EmailRegisterVO info) {
        String email = info.getEmail();
        String code = this.getEmailVerifyCode(email);
        String username = info.getUsername();

        if (code == null) {
            return "Please get the verification code first";
        }

        if (!code.equals(info.getCode())) {
            return "Incorrect verification code, please enter again";
        }

        if (this.existsAccountByEmail(email)) {
            return "This email address has been registered";
        }

        if (this.existsAccountByUsername(username))
            return "This username has been used by someone else";

        String password = passwordEncoder.encode(info.getPassword());
        Users user = new Users(null, username, email, password, null, null, null, null, LocalDateTime.now(), null);

        if (!this.save(user)) {
            return "Registration failed, please contact administrator";
        }

        // Add user role
        List<Integer> roleIds = List.of(2); // Default role id 2 is User role
        userPivotRolesService.updateUserRoles(user.getId(), roleIds);

        this.deleteEmailVerifyCode(email);
        return null;
    }

    /**
     * Confirm to reset the password and verify whether the verification code is correct
     *
     * @param info verification information
     * @return The operation result, null means normal, otherwise it is the cause of the error
     */
    public String resetConfirm(ConfirmResetVO info) {
        String email = info.getEmail();
        String code = this.getEmailVerifyCode(email);

        if (code == null)
            return "Please get the verification code first";

        if (!code.equals(info.getCode()))
            return "Incorrect verification code, please enter again";

        return null;
    }

    /**
     * Use email verification code to reset password
     * need to check whether the verification code is correct.
     *
     * @param info reset password information
     * @return The operation result, null means normal, otherwise it is the cause of the error
     */
    @Transactional(rollbackFor = Exception.class)
    public String resetEmailAccountPassword(EmailResetVO info) {
        String email = info.getEmail();
        String code = info.getCode();
        String password = passwordEncoder.encode(info.getPassword());

        String verify = resetConfirm(new ConfirmResetVO(email, code));

        if (verify != null)
            return verify;

        boolean update = this.update().eq("email", email).set("password", password).update();

        if (update) {
            this.deleteEmailVerifyCode(email);
        }

        return update ? null : "Reset failed, please contact administrator";
    }

    /**
     * Remove the email verification code stored in Redis
     *
     * @param email
     */
    private void deleteEmailVerifyCode(String email) {
        String key = Const.VERIFY_EMAIL_DATA + email;
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取Redis中存储的邮件验证码
     *
     * @param email 电邮
     * @return 验证码
     */
    private String getEmailVerifyCode(String email) {
        String key = Const.VERIFY_EMAIL_DATA + email;
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 针对IP地址进行邮件验证码获取限流
     *
     * @param address 地址
     * @return 是否通过验证
     */
    private boolean verifyLimit(String address) {
        String key = Const.VERIFY_EMAIL_LIMIT + address;
        return flow.limitOnceCheck(key, verifyLimit);
    }

    /**
     * 通过邮件地址查找用户
     *
     * @param email 邮件
     * @return 账户实体
     */
    public UserLoginDTO getUserByEmail(String email) {
        return this.baseMapper.getUserByEmail(email);
    }

    /**
     * 查询指定邮箱的用户是否已经存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    private boolean existsAccountByEmail(String email) {
        return this.baseMapper.exists(Wrappers.<Users>query().eq("email", email));
    }

    /**
     * 查询指定用户名的用户是否已经存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    private boolean existsAccountByUsername(String username) {
        return this.baseMapper.exists(Wrappers.<Users>query().eq("username", username));
    }
}
