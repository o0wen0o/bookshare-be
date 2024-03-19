package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.mapper.admin.UsersMapper;
import com.fyp.bookshare.service.admin.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public IPage<Users> getUsers(Page<Users> page, String filter) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("username", filter)
                    .or().like("email", filter)
                    .or().like("phone_number", filter)
                    .or().like("created_date", filter);
        }

        return this.baseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean updateUser(Integer id, Users user) {
        // Fetch the existing user to get the current details, especially the password
        Users existingUser = getById(id);
        if (existingUser == null) {
            // Handle the case where the user doesn't exist
            throw new IllegalArgumentException("User not found");
        }

        user.setId(id);

        // Check if a new password is provided
        boolean passwordNotEmpty = user.getPassword() != null && !user.getPassword().isEmpty();
        if (passwordNotEmpty) {
            // If a new password is provided, encode and set it
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // If the new password is not provided, use the existing one
            user.setPassword(existingUser.getPassword());
        }

        // Update the user with either the new password or the existing one
        return this.updateById(user);
    }
}
