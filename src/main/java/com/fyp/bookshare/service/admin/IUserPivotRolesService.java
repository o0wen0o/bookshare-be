package com.fyp.bookshare.service.admin;

import com.fyp.bookshare.pojo.UserPivotRoles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IUserPivotRolesService extends IService<UserPivotRoles> {

    List<UserPivotRoles> getUserRoles(Integer userId);

    boolean updateUserRoles(Integer id, List<Integer> roleIds);
}
