package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.PostsDTO;
import com.fyp.bookshare.pojo.Posts;
import com.fyp.bookshare.mapper.admin.PostsMapper;
import com.fyp.bookshare.service.admin.IPostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

    @Resource
    PostsMapper postsMapper;

    @Override
    public IPage<Posts> getPosts(Page<Posts> page, String filter) {
        QueryWrapper<Posts> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("content", filter)
                    .or().like("created_date", filter);
        }

        return postsMapper.selectPage(page, wrapper);
    }

    /**
     * get posts for community page
     *
     * @param page 分页
     * @param userId 用户id
     * @return 帖子的DTO
     */
    @Override
    public IPage<PostsDTO> getPostsDTO(Page<PostsDTO> page, Integer userId) {
        return postsMapper.getPostsDTO(page, userId);
    }

    @Override
    @Transactional
    public boolean createPost(Posts posts) {
        return postsMapper.insert(posts) > 0;
    }

    @Override
    @Transactional
    public boolean incrementLikes(Integer postId) {
        UpdateWrapper<Posts> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", postId)
                .setSql("likes = likes + 1");

        return postsMapper.update(null, updateWrapper) > 0;
    }

    @Override
    @Transactional
    public boolean decrementLikes(Integer postId) {
        UpdateWrapper<Posts> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", postId)
                .setSql("likes = likes - 1");

        return postsMapper.update(null, updateWrapper) > 0;
    }
}
