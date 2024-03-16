package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.BookSubmissions;
import com.fyp.bookshare.service.admin.IBookSubmissionsService;
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
@RequestMapping("/api/book-submissions")
public class BookSubmissionsController {

    @Resource
    IBookSubmissionsService bookSubmissionsService;

    @GetMapping("/")
    @Operation(summary = "Get a list of bookSubmissions")
    public RestBean<IPage<BookSubmissions>> getBookSubmissions(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<BookSubmissions> page = new Page<>(current, size);
        IPage<BookSubmissions> bookSubmissions = bookSubmissionsService.getBookSubmissions(page, filter);
        return RestBean.success(bookSubmissions);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a bookSubmission by its ID")
    public RestBean<BookSubmissions> getBookSubmissionById(@PathVariable Long id) {
        BookSubmissions bookSubmission = bookSubmissionsService.getById(id);
        if (bookSubmission != null) {
            return RestBean.success(bookSubmission);
        } else {
            return RestBean.failure(400, "BookSubmission not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new bookSubmission")
    public RestBean<Void> addBookSubmission(@RequestBody BookSubmissions bookSubmission) {
        return messageHandle(() -> bookSubmissionsService.save(bookSubmission), "Failed to add the bookSubmission");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing bookSubmission")
    public RestBean<Void> updateBookSubmission(@PathVariable Integer id, @RequestBody BookSubmissions bookSubmission) {
        bookSubmission.setId(id);
        return messageHandle(() -> bookSubmissionsService.updateById(bookSubmission), "Failed to update the bookSubmission");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a bookSubmission")
    public RestBean<Void> deleteBookSubmissions(@PathVariable List<Integer> ids) {
        return messageHandle(() -> bookSubmissionsService.removeByIds(ids), "Failed to delete the bookSubmission");
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
