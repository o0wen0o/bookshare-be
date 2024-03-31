package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyp.bookshare.pojo.PostLikes;
import com.fyp.bookshare.mapper.admin.PostLikesMapper;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IPostLikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.admin.IPostsService;
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
public class PostLikesServiceImpl extends ServiceImpl<PostLikesMapper, PostLikes> implements IPostLikesService {

    @Resource
    private PostLikesMapper postLikesMapper;

    @Resource
    private IPostsService postsService;

    @Override
    @Transactional
    public boolean unlikePost(Integer userId, Integer postId) {
        // Step 1: Delete the like record
        QueryWrapper<PostLikes> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("user_id", userId).eq("post_id", postId);
        int deleteCount = postLikesMapper.delete(deleteWrapper);

        if (deleteCount > 0) {
            // Step 2: Decrement likes count in posts
            return postsService.decrementLikes(postId);
        }

        return false;
    }
}
