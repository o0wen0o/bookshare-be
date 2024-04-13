package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.UserSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.mapper.admin.UsersMapper;
import com.fyp.bookshare.service.admin.IUserPivotRolesService;
import com.fyp.bookshare.service.admin.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.impl.OssServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private IUserPivotRolesService userPivotRolesService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    OssServiceImpl ossService;

    @Override
    public IPage<Users> getUsers(Page<Users> page, String filter) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("username", filter)
                    .or().like("email", filter)
                    .or().like("phone_number", filter)
                    .or().like("created_date", filter);
        }

        return usersMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<UserSelectionsDTO> getUserSelections(Page<Books> page, String filter) {
        return usersMapper.selectUserSelectionsWithPagination(page, filter);
    }

    @Override
    @Transactional
    public boolean addUser(Users user, List<Integer> roleIds, MultipartFile image) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt the password

        // Save the user without the avatar first to generate the user ID
        boolean userSaved = this.save(user);

        // Proceed only if the user was successfully saved
        if (userSaved && image != null && !image.isEmpty()) {
            String imageUrl = "users/avatar/" + ossService.generateFileName(user.getId(), image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss

            // Update the user with the avatar URL
            user.setAvatar(imageUrl);
            this.updateById(user);
        }

        // Update the user roles
        if (userSaved && roleIds != null) {
            userPivotRolesService.updateUserRoles(user.getId(), roleIds);
        }

        return userSaved;
    }

    @Override
    @Transactional
    public boolean updateUser(Integer id, Users user, List<Integer> roleIds, MultipartFile image) {
        // Fetch the existing user to get the current details, especially the password
        Users existingUser = this.getById(id);
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

        // Check if an image is provided
        if (image != null && !image.isEmpty()) {
            String imageUrl = "users/avatar/" + ossService.generateFileName(id, image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss
            user.setAvatar(imageUrl);
        }

        // Update the user roles
        if (roleIds != null) {
            boolean updated = userPivotRolesService.updateUserRoles(id, roleIds);
        }

        // Update the user with either the new password or the existing one
        return this.updateById(user);
    }

    @Override
    public Boolean checkBookshelfVisible(Integer userId) {
        Users user = this.getById(userId);
        if (user != null) {
            return user.getBookshelfVisible();
        }
        return false;
    }

    @Override
    public Boolean checkBookReviewVisible(Integer userId) {
        Users user = this.getById(userId);
        if (user != null) {
            return user.getReviewVisible();
        }
        return false;
    }

    @Override
    public Boolean checkDonationVisible(Integer userId) {
        Users user = this.getById(userId);
        if (user != null) {
            return user.getContributionVisible();
        }
        return false;
    }
}
