package com.fyp.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.bookshare.entity.dto.UserDTO;
import com.fyp.bookshare.entity.vo.request.ConfirmResetVO;
import com.fyp.bookshare.entity.vo.request.EmailRegisterVO;
import com.fyp.bookshare.entity.vo.request.EmailResetVO;
import com.fyp.bookshare.pojo.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<Users>, UserDetailsService {
    UserDTO getUserByEmail(String text);

    String registerEmailVerifyCode(String type, String email, String address);

    String registerEmailAccount(EmailRegisterVO info);

    String resetConfirm(ConfirmResetVO info);

    String resetEmailAccountPassword(EmailResetVO info);
}
