package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyp.bookshare.pojo.BookCommentLikes;
import com.fyp.bookshare.mapper.admin.BookCommentLikesMapper;
import com.fyp.bookshare.pojo.PostCommentLikes;
import com.fyp.bookshare.service.admin.IBookCommentLikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.admin.IBookCommentsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class BookCommentLikesServiceImpl extends ServiceImpl<BookCommentLikesMapper, BookCommentLikes> implements IBookCommentLikesService {

    @Resource
    private BookCommentLikesMapper bookCommentLikesMapper;

    @Resource
    private IBookCommentsService bookCommentsService;

    @Override
    @Transactional
    public boolean likePostComment(Integer bookCommentId, Integer userId) {
        // Step 1: Create a new like record
        boolean save = this.save(new BookCommentLikes(null, bookCommentId, userId));

        if (save) {
            // Step 2: Increment likes count in post comments
            return bookCommentsService.incrementLikes(bookCommentId);
        }

        return false;
    }

    @Override
    @Transactional
    public boolean unlikePostComment(Integer bookCommentId, Integer userId) {
        // Step 1: Delete the like record
        QueryWrapper<BookCommentLikes> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("user_id", userId).eq("book_comment_id", bookCommentId);
        int deleteCount = bookCommentLikesMapper.delete(deleteWrapper);

        if (deleteCount > 0) {
            // Step 2: Decrement likes count in post comments
            return bookCommentsService.decrementLikes(bookCommentId);
        }

        return false;
    }
}
