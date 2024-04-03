package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.BookSelectionsDTO;
import com.fyp.bookshare.entity.dto.PostsEditDTO;
import com.fyp.bookshare.entity.dto.UserSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Posts;
import com.fyp.bookshare.service.admin.IBooksService;
import com.fyp.bookshare.service.admin.IPostsService;
import com.fyp.bookshare.service.admin.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Resource
    IPostsService postsService;

    @Resource
    IBooksService booksService;

    @Resource
    IUsersService usersService;

    @GetMapping("/")
    @Operation(summary = "Get a list of posts")
    public RestBean<IPage<Posts>> getPosts(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Posts> page = new Page<>(current, size);
        IPage<Posts> posts = postsService.getPosts(page, filter);
        return RestBean.success(posts);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a post by its ID")
    public RestBean<PostsEditDTO> getPostById(@PathVariable Integer id) {
        PostsEditDTO post = postsService.getPostById(id);
        if (post != null) {
            return RestBean.success(post);
        } else {
            return RestBean.failure(400, "Post not found");
        }
    }

    @GetMapping("/getUserSelections")
    @Operation(summary = "Get a list of user selections")
    public RestBean<IPage<UserSelectionsDTO>> getUserSelections(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Books> page = new Page<>(current, size);

        IPage<UserSelectionsDTO> bookSelectionsDTOs = usersService.getUserSelections(page, filter);
        return RestBean.success(bookSelectionsDTOs);
    }

    @GetMapping("/getBookSelections")
    @Operation(summary = "Get a list of book selections")
    public RestBean<IPage<BookSelectionsDTO>> getBookSelections(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Books> page = new Page<>(current, size);

        IPage<BookSelectionsDTO> bookSelectionsDTOs = booksService.getBookSelections(page, filter);
        return RestBean.success(bookSelectionsDTOs);
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new post")
    public RestBean<Void> addPost(@RequestBody Posts post) {
        return messageHandle(() -> postsService.save(post), "Failed to add the post");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing post")
    public RestBean<Void> updatePost(@PathVariable Integer id, @RequestBody Posts post) {
        post.setId(id);
        return messageHandle(() -> postsService.updateById(post), "Failed to update the post");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a post")
    public RestBean<Void> deletePosts(@PathVariable List<Integer> ids) {
        return messageHandle(() -> postsService.removeByIds(ids), "Failed to delete the post");
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
