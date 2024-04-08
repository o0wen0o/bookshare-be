package com.fyp.bookshare.service.admin;

import com.fyp.bookshare.pojo.UserPivotGenres;
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
public interface IUserPivotGenresService extends IService<UserPivotGenres> {

    List<UserPivotGenres> getFavouriteGenres(Integer userId);

    boolean saveFavouriteGenres(List<Integer> ids, Integer userId);
}
