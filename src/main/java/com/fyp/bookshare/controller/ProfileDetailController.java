package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.UserDTO;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BooleanSupplier;

/**
 * @author o0wen0o
 * @create 2024-04-08 11:16 PM
 */
@RestController
@RequestMapping("/api/profile-detail")
public class ProfileDetailController {

    @Resource
    IUsersService usersService;

    @GetMapping("/getUserDetail/{id}")
    @Operation(summary = "Get a user by user ID")
    public RestBean<Users> getUserById(@PathVariable Long id) {
        Users user = usersService.getById(id);
        user.setPassword(null);
        return RestBean.success(user);
    }

    @PutMapping("/updateUserDetail/{id}")
    @Operation(summary = "Update an existing user")
    public RestBean<Void> updateUserDetail(@PathVariable Integer id, @ModelAttribute UserDTO userDTO, MultipartFile image) {
        Users user = userDTO.asViewObject(Users.class);
        return messageHandle(() -> usersService.updateUser(id, user, null, image), "Failed to update the user");
    }

    /**
     * 针对于返回值为String作为错误信息的方法进行统一处理
     *
     * @param action 具体操作
     * @return 响应结果
     */
    private RestBean<Void> messageHandle(BooleanSupplier action, String failureMessage) {
        boolean result = action.getAsBoolean();
        if (result) {
            return RestBean.success();
        } else {
            return RestBean.failure(400, failureMessage);
        }
    }

}
