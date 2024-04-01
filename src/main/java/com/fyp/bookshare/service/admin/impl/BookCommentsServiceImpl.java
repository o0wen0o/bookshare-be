package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.BookComments;
import com.fyp.bookshare.mapper.admin.BookCommentsMapper;
import com.fyp.bookshare.service.admin.IBookCommentsService;
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
public class BookCommentsServiceImpl extends ServiceImpl<BookCommentsMapper, BookComments> implements IBookCommentsService {

    @Override
    public IPage<BookComments> getBookCommentsByBookId(Page<BookComments> page, Integer bookId, String filter) {
        QueryWrapper<BookComments> wrapper = new QueryWrapper<>();

        if (bookId != null) {
            wrapper.eq("book_id", bookId);
        }

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("text", filter)
                    .or().like("created_date", filter);
        }

        return this.baseMapper.selectPage(page, wrapper);
    }
}
