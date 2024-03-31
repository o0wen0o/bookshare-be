package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.PostCommentsDTO;
import com.fyp.bookshare.entity.dto.PostsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Posts;
import com.fyp.bookshare.service.admin.IBookCommentsService;
import com.fyp.bookshare.service.admin.IBooksService;
import com.fyp.bookshare.service.admin.IPostCommentsService;
import com.fyp.bookshare.service.admin.IPostsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author o0wen0o
 * @create 2024-03-31 10:26 PM
 */
@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Resource
    IPostsService postsService;

    @Resource
    IPostCommentsService postCommentsService;

    @GetMapping("/getPosts")
    @Operation(summary = "Get a list of posts")
    public RestBean<IPage<PostsDTO>> getPostsDTO(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "10"));
        Integer userId = Integer.valueOf(params.getOrDefault("userId", null));

        Page<PostsDTO> page = new Page<>(current, size);
        IPage<PostsDTO> postsDTO = postsService.getPostsDTO(page, userId);
        return RestBean.success(postsDTO);
    }

    @GetMapping("/getPostComments")
    @Operation(summary = "Get a list of post comments")
    public RestBean<List<PostCommentsDTO>> getPostCommentsDTO(@RequestParam Map<String, String> params) {
        Integer postId = Integer.valueOf(params.getOrDefault("postId", null));
        Integer userId = Integer.valueOf(params.getOrDefault("userId", null));

        List<PostCommentsDTO> postCommentsDTO = postCommentsService.getPostCommentsDTO(postId, userId);
        return RestBean.success(postCommentsDTO);
    }
}
