package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.PostsDTO;
import com.fyp.bookshare.entity.dto.PostsEditDTO;
import com.fyp.bookshare.pojo.Posts;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IPostsService extends IService<Posts> {

    IPage<Posts> getPosts(Page<Posts> page, String filter);

    IPage<PostsDTO> getPostsDTO(Page<PostsDTO> page, Integer userId);

    PostsEditDTO getPostById(Integer id);

    boolean createPost(Posts posts);

    boolean incrementLikes(Integer postId);

    boolean decrementLikes(Integer postId);
}
