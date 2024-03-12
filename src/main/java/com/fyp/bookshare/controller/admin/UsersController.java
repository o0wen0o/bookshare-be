package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    /**
     * 获取用户列表
     *
     * @param params 请求参数
     * @return 响应结果
     */
    @GetMapping("/")
    @Operation(summary = "获取用户列表")
    public RestBean<IPage<Users>> getUsers(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Users> page = new Page<>(current, size);
        IPage<Users> users = usersService.getUsers(page, filter);
        return RestBean.success(users);
    }
}
