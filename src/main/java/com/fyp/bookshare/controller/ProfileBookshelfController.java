package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.service.admin.IBookshelfPivotBooksService;
import com.fyp.bookshare.service.admin.IBookshelvesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * @author o0wen0o
 * @create 2024-04-05 10:37 AM
 */
@RestController
@RequestMapping("/api/profile-bookshelf")
public class ProfileBookshelfController {

    @Resource
    IBookshelvesService bookshelvesService;

    @Resource
    IBookshelfPivotBooksService bookshelfPivotBooksService;

    @GetMapping("/getFavouriteBooks")
    @Operation(summary = "Get a list of favourite books")
    public RestBean<IPage<Books>> getFavouriteBooks(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "10"));
        Integer userId = Integer.valueOf(params.getOrDefault("userId", ""));

        Page<Books> page = new Page<>(current, size);
        IPage<Books> books = bookshelvesService.getFavouriteBooks(page, userId);
        return RestBean.success(books);
    }

    @DeleteMapping("/deleteFromBookshelf/{bookId}/{userId}")
    @Operation(summary = "Delete book from bookshelf")
    public RestBean<Void> deleteFromBookshelf(@PathVariable Integer bookId, @PathVariable Integer userId) {
        return messageHandle(() -> bookshelfPivotBooksService.deleteFromBookshelf(bookId, userId), "Failed to unlike the book comment");
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
