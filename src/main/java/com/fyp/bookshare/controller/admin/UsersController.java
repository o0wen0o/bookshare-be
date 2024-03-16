package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@RestController
@Slf4j
@RequestMapping("/api/users")
public class UsersController {

    @Resource
    IUsersService usersService;

    @GetMapping("/")
    @Operation(summary = "Get a list of users")
    public RestBean<IPage<Users>> getUsers(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Users> page = new Page<>(current, size);
        IPage<Users> users = usersService.getUsers(page, filter);
        return RestBean.success(users);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a user by its ID")
    public RestBean<Users> getUserById(@PathVariable Long id) {
        Users user = usersService.getById(id);
        if (user != null) {
            return RestBean.success(user);
        } else {
            return RestBean.failure(400, "User not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new user")
    public RestBean<Void> addUser(@RequestBody Users user) {
        return messageHandle(() -> usersService.save(user), "Failed to add the user");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing user")
    public RestBean<Void> updateUser(@PathVariable Integer id, @RequestBody Users user) {
        user.setId(id);
        return messageHandle(() -> usersService.updateById(user), "Failed to update the user");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a user")
    public RestBean<Void> deleteUsers(@PathVariable List<Integer> ids) {
        return messageHandle(() -> usersService.removeByIds(ids), "Failed to delete the user");
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
