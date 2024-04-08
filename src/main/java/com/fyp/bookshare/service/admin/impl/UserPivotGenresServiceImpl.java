package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyp.bookshare.pojo.UserPivotGenres;
import com.fyp.bookshare.mapper.admin.UserPivotGenresMapper;
import com.fyp.bookshare.service.admin.IUserPivotGenresService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserPivotGenresServiceImpl extends ServiceImpl<UserPivotGenresMapper, UserPivotGenres> implements IUserPivotGenresService {

    @Resource
    UserPivotGenresMapper userPivotGenresMapper;

    @Override
    public List<UserPivotGenres> getFavouriteGenres(Integer userId) {
        QueryWrapper<UserPivotGenres> wrapper = new QueryWrapper<>();

        if (userId != null) {
            wrapper.eq("user_id", userId);
        }

        return userPivotGenresMapper.selectList(wrapper);
    }

    /**
     * @param ids    these are new genre ids
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public boolean saveFavouriteGenres(List<Integer> ids, Integer userId) {
        List<UserPivotGenres> oldFavouriteGenres = getFavouriteGenres(userId);
        List<UserPivotGenres> genresToRemove = new ArrayList<>();
        List<UserPivotGenres> genresToAdd = new ArrayList<>();

        // Identify old favourite genres to remove
        oldFavouriteGenres.forEach(userPivotGenre -> {
            if (!ids.contains(userPivotGenre.getGenreId())) {
                genresToRemove.add(userPivotGenre);
            }
        });

        // Remove old favourite genres that are not in the new list
        genresToRemove.forEach(genre -> userPivotGenresMapper.deleteById(genre.getId()));

        // Identify and prepare new favourite genres to add
        ids.forEach(id -> {
            if (oldFavouriteGenres.stream().noneMatch(userPivotGenre -> userPivotGenre.getGenreId().equals(id))) {
                genresToAdd.add(new UserPivotGenres(null, userId, id));
            }
        });

        // Add new favourite genres
        genresToAdd.forEach(genre -> userPivotGenresMapper.insert(genre));

        return true;
    }
}
