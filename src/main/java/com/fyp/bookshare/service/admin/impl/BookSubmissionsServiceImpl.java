package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.BookSubmissions;
import com.fyp.bookshare.mapper.admin.BookSubmissionsMapper;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IBookSubmissionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class BookSubmissionsServiceImpl extends ServiceImpl<BookSubmissionsMapper, BookSubmissions> implements IBookSubmissionsService {

    @Override
    public IPage<BookSubmissions> getBookSubmissions(Page<BookSubmissions> page, String filter) {
        QueryWrapper<BookSubmissions> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("title", filter)
                    .or().like("author", filter)
                    .or().like("publisher", filter)
                    .or().like("isbn", filter)
                    .or().like("publication_date", filter)
                    .or().like("language", filter)
                    .or().like("created_date", filter)
                    .or().like("status", filter);
        }

        return this.baseMapper.selectPage(page, wrapper);
    }
}
