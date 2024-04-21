package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.vo.request.ConfirmResetVO;
import com.fyp.bookshare.entity.vo.request.EmailRegisterVO;
import com.fyp.bookshare.entity.vo.request.EmailResetVO;
import com.fyp.bookshare.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

/**
 * Use for verification including user registration, password reset
 */
@Validated
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Login verification related", description = "Including user login, registration, verification code request and other operations")
public class AuthorizeController {

    @Resource
    AccountService accountService;

    /**
     * Request email verification code
     *
     * @param type    either register or reset
     * @param email   email
     * @param request to get the IP address
     * @return Whether the request is successful
     */
    @GetMapping("/ask-code")
    @Operation(summary = "Request email verification code")
    public RestBean<Void> askVerifyCode(@RequestParam @Pattern(regexp = "(register|reset)") String type,
                                        @RequestParam @Email String email,
                                        HttpServletRequest request) {
        return this.messageHandle(() ->
                accountService.registerEmailVerifyCode(type, email, request.getRemoteAddr()));
    }

    /**
     * To perform user registration, you need to request an email verification code first
     *
     * @param vo registration information
     * @return Whether registration is successful
     */
    @PostMapping("/register")
    @Operation(summary = "User registration operation")
    public RestBean<Void> register(@RequestBody @Valid EmailRegisterVO vo) {
        return this.messageHandle(() ->
                accountService.registerEmailAccount(vo));
    }

    /**
     * Perform password reset confirmation and check whether the verification code is correct
     *
     * @param vo password reset information
     * @return Whether the operation was successful
     */
    @PostMapping("/reset-confirm")
    @Operation(summary = "Password reset confirmation")
    public RestBean<Void> resetConfirm(@RequestBody @Valid ConfirmResetVO vo) {
        return this.messageHandle(() -> accountService.resetConfirm(vo));
    }

    /**
     * Perform password reset operation
     *
     * @param vo password reset information
     * @return Whether the operation was successful
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Password reset operation")
    public RestBean<Void> resetPassword(@RequestBody @Valid EmailResetVO vo) {
        return this.messageHandle(() ->
                accountService.resetEmailAccountPassword(vo));
    }

    /**
     * 针对于返回值为String作为错误信息的方法进行统一处理
     *
     * @param action 具体操作
     * @param <T>    响应结果类型
     * @return 响应结果
     */
    private <T> RestBean<T> messageHandle(Supplier<String> action) {
        String message = action.get();
        if (message == null)
            return RestBean.success();
        else
            return RestBean.failure(400, message);
    }
}
