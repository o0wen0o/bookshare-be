package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.PostCommentsDTO;
import com.fyp.bookshare.entity.dto.PostsDTO;
import com.fyp.bookshare.service.admin.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

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

    @Resource
    IPostLikesService postLikesService;

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

    @DeleteMapping("/unlikePost/{postId}/{userId}")
    @Operation(summary = "Unlike a post")
    public RestBean<Void> unlikePost(@PathVariable Integer postId, @PathVariable Integer userId) {
        return messageHandle(() -> postLikesService.unlikePost(userId, postId), "Failed to unlike the post");
    }

    /**
     * 针对于返回值为String作为错误信息的方法进行统一处理
     *
     * @param action 具体操作
     * @return 响应结果
     */
    private RestBean<Void> messageHandle(BooleanSupplier action, String failureMessage) {
        boolean result = action.getAsBoolean();
        if (result) {
            return RestBean.success();
        } else {
            return RestBean.failure(400, failureMessage);
        }
    }
}
