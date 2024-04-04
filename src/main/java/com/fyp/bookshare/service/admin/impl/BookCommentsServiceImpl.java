package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookCommentsDTO;
import com.fyp.bookshare.pojo.BookComments;
import com.fyp.bookshare.mapper.admin.BookCommentsMapper;
import com.fyp.bookshare.pojo.PostComments;
import com.fyp.bookshare.service.admin.IBookCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Resource
    BookCommentsMapper bookCommentsMapper;

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

        return bookCommentsMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<BookCommentsDTO> getCommentsDTO(Page<BookCommentsDTO> page, Integer bookId, Integer userId) {
        return bookCommentsMapper.getCommentsDTO(page, bookId, userId);
    }

    @Override
    public List<BookCommentsDTO> getCommentReplies(Integer bookCommentId, Integer userId) {
        return bookCommentsMapper.getCommentReplies(bookCommentId, userId);
    }

    @Override
    public Integer createBookComment(BookComments bookComments) {
        if (bookCommentsMapper.insert(bookComments) > 0) {
            return bookComments.getId();
        }
        return 0;
    }

    @Override
    @Transactional
    public boolean incrementLikes(Integer bookCommentId) {
        UpdateWrapper<BookComments> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", bookCommentId)
                .setSql("likes = likes + 1");

        return bookCommentsMapper.update(null, updateWrapper) > 0;
    }

    @Override
    @Transactional
    public boolean decrementLikes(Integer bookCommentId) {
        UpdateWrapper<BookComments> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", bookCommentId)
                .setSql("likes = likes - 1");

        return bookCommentsMapper.update(null, updateWrapper) > 0;
    }

}
