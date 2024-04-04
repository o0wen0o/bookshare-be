package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.BookCommentsDTO;
import com.fyp.bookshare.entity.dto.BookDetailDTO;
import com.fyp.bookshare.entity.dto.PostCommentsDTO;
import com.fyp.bookshare.entity.dto.PostsDTO;
import com.fyp.bookshare.pojo.BookCommentLikes;
import com.fyp.bookshare.pojo.BookComments;
import com.fyp.bookshare.pojo.PostCommentLikes;
import com.fyp.bookshare.pojo.PostComments;
import com.fyp.bookshare.service.admin.IBookCommentLikesService;
import com.fyp.bookshare.service.admin.IBookCommentsService;
import com.fyp.bookshare.service.admin.IBooksService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * @author o0wen0o
 * @create 2024-04-04 11:39 AM
 */
@RestController
@Slf4j
@RequestMapping("/api/book-detail")
public class BookDetailController {

    @Resource
    IBooksService booksService;

    @Resource
    IBookCommentsService bookCommentsService;

    @Resource
    IBookCommentLikesService bookCommentLikesService;

    @GetMapping("/getBookDetail/{bookId}/{userId}")
    @Operation(summary = "Get book detail")
    public RestBean<BookDetailDTO> getBookDetail(@PathVariable Integer bookId, @PathVariable Integer userId) {
        BookDetailDTO bookDetailDTO = booksService.getBookDetail(bookId, userId);
        return RestBean.success(bookDetailDTO);
    }

    @GetMapping("/getRecommendedBooks/{bookId}")
    @Operation(summary = "Get recommended books")
    public RestBean<List<BookDetailDTO>> getRecommendedBooks(@PathVariable Integer bookId) {
        List<BookDetailDTO> bookDetailDTOs = booksService.getRecommendedBooks(bookId);
        return RestBean.success(bookDetailDTOs);
    }

    @GetMapping("/getComments")
    @Operation(summary = "Get a list of post comments")
    public RestBean<IPage<BookCommentsDTO>> getCommentsDTO(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "10"));
        Integer bookId = Integer.valueOf(params.getOrDefault("bookId", null));
        Integer userId = Integer.valueOf(params.getOrDefault("userId", null));

        Page<BookCommentsDTO> page = new Page<>(current, size);
        IPage<BookCommentsDTO> bookCommentsDTOs = bookCommentsService.getCommentsDTO(page, bookId, userId);
        return RestBean.success(bookCommentsDTOs);
    }

    @GetMapping("/getCommentReplies")
    @Operation(summary = "Get a list of comment replies")
    public RestBean<List<BookCommentsDTO>> getCommentReplies(@RequestParam Map<String, String> params) {
        Integer bookCommentId = Integer.valueOf(params.getOrDefault("bookCommentId", null));
        Integer userId = Integer.valueOf(params.getOrDefault("userId", null));

        List<BookCommentsDTO> commentReplies = bookCommentsService.getCommentReplies(bookCommentId, userId);
        return RestBean.success(commentReplies);
    }

    @PostMapping("/createBookComment")
    @Operation(summary = "Create a book comment")
    public RestBean<Integer> createBookComment(@RequestBody BookComments bookComments) {
        Integer bookCommentId = bookCommentsService.createBookComment(bookComments);
        return bookCommentId != null ?
                RestBean.success(bookCommentId) :
                RestBean.failure(400, "Failed to create the post comment");
    }

    @PostMapping("/likeBookComment")
    @Operation(summary = "Like a book comment")
    public RestBean<Void> likeBookComment(@RequestBody BookCommentLikes bookCommentLikes) {
        Integer bookCommentId = bookCommentLikes.getBookCommentId();
        Integer userId = bookCommentLikes.getUserId();

        return messageHandle(() -> bookCommentLikesService.likePostComment(bookCommentId, userId), "Failed to like the book comment");
    }

    @DeleteMapping("/unlikeBookComment/{bookCommentId}/{userId}")
    @Operation(summary = "Unlike a book comment")
    public RestBean<Void> unlikeBookComment(@PathVariable Integer bookCommentId, @PathVariable Integer userId) {
        return messageHandle(() -> bookCommentLikesService.unlikePostComment(bookCommentId, userId), "Failed to unlike the book comment");
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
