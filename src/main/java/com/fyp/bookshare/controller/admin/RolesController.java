package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Roles;
import com.fyp.bookshare.service.admin.IRolesService;
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
@RequestMapping("/api/roles")
public class RolesController {

    @Resource
    IRolesService rolesService;

    @GetMapping("/")
    @Operation(summary = "Get a list of roles")
    public RestBean<IPage<Roles>> getRoles(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Roles> page = new Page<>(current, size);
        IPage<Roles> roles = rolesService.getRoles(page, filter);
        return RestBean.success(roles);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a role by its ID")
    public RestBean<Roles> getUserById(@PathVariable Long id) {
        Roles role = rolesService.getById(id);
        if (role != null) {
            return RestBean.success(role);
        } else {
            return RestBean.failure(400, "User not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new role")
    public RestBean<Void> addUser(@RequestBody Roles role) {
        return messageHandle(() -> rolesService.save(role), "Failed to add the role");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing role")
    public RestBean<Void> updateUser(@PathVariable Integer id, @RequestBody Roles role) {
        role.setId(id);
        return messageHandle(() -> rolesService.updateById(role), "Failed to update the role");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a role")
    public RestBean<Void> deleteRoles(@PathVariable List<Integer> ids) {
        return messageHandle(() -> rolesService.removeByIds(ids), "Failed to delete the role");
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
