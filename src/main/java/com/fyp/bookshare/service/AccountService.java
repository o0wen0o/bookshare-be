package com.fyp.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.bookshare.entity.dto.UserDTO;
import com.fyp.bookshare.entity.vo.request.ConfirmResetVO;
import com.fyp.bookshare.entity.vo.request.EmailRegisterVO;
import com.fyp.bookshare.entity.vo.request.EmailResetVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<UserDTO>, UserDetailsService {
    UserDTO getUserByEmail(String text);

    String registerEmailVerifyCode(String type, String email, String address);

    String registerEmailAccount(EmailRegisterVO info);

    String resetEmailAccountPassword(EmailResetVO info);

    String resetConfirm(ConfirmResetVO info);
}
