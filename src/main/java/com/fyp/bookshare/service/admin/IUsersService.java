package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.UserSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IUsersService extends IService<Users> {

    IPage<Users> getUsers(Page<Users> page, String filter);

    IPage<UserSelectionsDTO> getUserSelections(Page<Books> page, String filter);

    boolean updateUser(Integer id, Users user, List<Integer> roleIds, MultipartFile image);

    boolean addUser(Users user, MultipartFile image);
}
