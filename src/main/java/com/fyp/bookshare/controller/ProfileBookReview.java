package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.BookReviewDTO;
import com.fyp.bookshare.entity.dto.BookSelectionsDTO;
import com.fyp.bookshare.pojo.BookReviews;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.service.admin.IBookReviewsService;
import com.fyp.bookshare.service.admin.IBooksService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * @author o0wen0o
 * @create 2024-04-08 2:45 PM
 */
@RestController
@RequestMapping("/api/profile-book-review")
public class ProfileBookReview {

    @Resource
    IBookReviewsService bookReviewsService;

    @Resource
    IBooksService booksService;

    @GetMapping("/getBookReviewsByUserId")
    @Operation(summary = "Get a list of book review by user id")
    public RestBean<IPage<BookReviewDTO>> getBookReviewsByUserId(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "10"));
        Integer userId = Integer.valueOf(params.getOrDefault("userId", ""));

        Page<BookReviewDTO> page = new Page<>(current, size);
        IPage<BookReviewDTO> bookReviews = bookReviewsService.getBookReviewsByUserId(page, userId);
        return RestBean.success(bookReviews);
    }

    @GetMapping("/getBookReviewById")
    @Operation(summary = "Get a book review by id")
    public RestBean<BookReviewDTO> getBookReviewById(@RequestParam Map<String, String> params) {
        Integer id = Integer.valueOf(params.getOrDefault("id", ""));
        BookReviewDTO bookReview = bookReviewsService.getBookReviewById(id);
        return RestBean.success(bookReview);
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

    @PostMapping("/createBookReview")
    @Operation(summary = "Create a book review")
    public RestBean<Void> createBookReview(@RequestBody BookReviews bookReview) {
        return messageHandle(() -> bookReviewsService.createBookReview(bookReview), "Failed to create the book review");
    }

    @PutMapping("/updateBookReview")
    @Operation(summary = "Update a book review")
    public RestBean<Void> updateBookReview(@RequestBody BookReviews bookReview) {
        return messageHandle(() -> bookReviewsService.updateBookReview(bookReview), "Failed to update the book review");
    }

    @DeleteMapping("/deleteBookReview/{bookReviewId}")
    @Operation(summary = "Delete book review by id")
    public RestBean<Void> deleteBookReview(@PathVariable Integer bookReviewId) {
        return messageHandle(() -> bookReviewsService.removeById(bookReviewId), "Failed to delete the book review");
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
