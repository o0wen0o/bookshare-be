package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.BookComments;
import com.fyp.bookshare.service.admin.IBookCommentsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/book-comments")
public class BookCommentsController {

    @Resource
    IBookCommentsService bookCommentsService;

    @GetMapping("/")
    @Operation(summary = "Get a list of bookComments")
    public RestBean<IPage<BookComments>> getBookComments(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<BookComments> page = new Page<>(current, size);
        IPage<BookComments> bookComments = bookCommentsService.getBookComments(page, filter);
        return RestBean.success(bookComments);
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
