package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyp.bookshare.pojo.PostCommentLikes;
import com.fyp.bookshare.mapper.admin.PostCommentLikesMapper;
import com.fyp.bookshare.pojo.PostLikes;
import com.fyp.bookshare.service.admin.IPostCommentLikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.admin.IPostCommentsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class PostCommentLikesServiceImpl extends ServiceImpl<PostCommentLikesMapper, PostCommentLikes> implements IPostCommentLikesService {

    @Resource
    private PostCommentLikesMapper postCommentLikesMapper;

    @Resource
    private IPostCommentsService postCommentsService;

    @Override
    @Transactional
    public boolean likePostComment(Integer postCommentId, Integer userId) {
        // Step 1: Create a new like record
        boolean save = this.save(new PostCommentLikes(null, postCommentId, userId));

        if (save) {
            // Step 2: Increment likes count in post comments
            return postCommentsService.incrementLikes(postCommentId);
        }

        return false;
    }

    @Override
    @Transactional
    public boolean unlikePostComment(Integer postCommentId, Integer userId) {
        // Step 1: Delete the like record
        QueryWrapper<PostCommentLikes> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("user_id", userId).eq("post_comment_id", postCommentId);
        int deleteCount = postCommentLikesMapper.delete(deleteWrapper);

        if (deleteCount > 0) {
            // Step 2: Decrement likes count in post comments
            return postCommentsService.decrementLikes(postCommentId);
        }

        return false;
    }
}
