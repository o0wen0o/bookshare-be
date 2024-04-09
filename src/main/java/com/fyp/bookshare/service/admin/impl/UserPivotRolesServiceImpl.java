package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyp.bookshare.pojo.UserPivotRoles;
import com.fyp.bookshare.mapper.admin.UserPivotRolesMapper;
import com.fyp.bookshare.service.admin.IUserPivotRolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class UserPivotRolesServiceImpl extends ServiceImpl<UserPivotRolesMapper, UserPivotRoles> implements IUserPivotRolesService {


    @Resource
    UserPivotRolesMapper userPivotRolesMapper;

    @Override
    public List<UserPivotRoles> getUserRoles(Integer userId) {
        QueryWrapper<UserPivotRoles> wrapper = new QueryWrapper<>();

        if (userId != null) {
            wrapper.eq("user_id", userId);
        }

        return userPivotRolesMapper.selectList(wrapper);
    }

    @Override
    public boolean updateUserRoles(Integer userId, List<Integer> roleIds) {
        List<UserPivotRoles> oldUserRoles = getUserRoles(userId);
        List<UserPivotRoles> rolesToRemove = new ArrayList<>();
        List<UserPivotRoles> rolesToAdd = new ArrayList<>();

        // Identify old roles to remove
        oldUserRoles.forEach(userPivotRole -> {
            if (!roleIds.contains(userPivotRole.getRoleId())) {
                rolesToRemove.add(userPivotRole);
            }
        });

        // Remove old roles that are not in the new list
        rolesToRemove.forEach(role -> userPivotRolesMapper.deleteById(role.getId()));

        // Identify and prepare new roles to add
        roleIds.forEach(id -> {
            if (oldUserRoles.stream().noneMatch(userPivotRole -> userPivotRole.getRoleId().equals(id))) {
                rolesToAdd.add(new UserPivotRoles(null, userId, id));
            }
        });

        // Add new roles
        rolesToAdd.forEach(role -> userPivotRolesMapper.insert(role));

        return true;
    }
}
