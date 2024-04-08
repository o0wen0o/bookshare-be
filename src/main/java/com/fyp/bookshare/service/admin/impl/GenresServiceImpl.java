package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.Genres;
import com.fyp.bookshare.mapper.admin.GenresMapper;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IGenresService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
public class GenresServiceImpl extends ServiceImpl<GenresMapper, Genres> implements IGenresService {

    @Resource
    GenresMapper genresMapper;

    @Override
    public IPage<Genres> getGenres(Page<Genres> page, String filter) {
        QueryWrapper<Genres> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("name", filter);
        }

        return genresMapper.selectPage(page, wrapper);
    }
}
