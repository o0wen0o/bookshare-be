package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.BookSubmissions;
import com.fyp.bookshare.service.admin.IBookSubmissionsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BooleanSupplier;

/**
 * @author o0wen0o
 * @create 2024-04-08 12:04 PM
 */
@RestController
@RequestMapping("/api/profile-book-contribution")
public class ProfileBookContributionController {

    @Resource
    IBookSubmissionsService bookSubmissionsService;

    @PostMapping("/createBookSubmission")
    @Operation(summary = "Add a new book submission")
    public RestBean<Void> addBookSubmission(@ModelAttribute BookSubmissions bookSubmission, MultipartFile image) {
        return messageHandle(() -> bookSubmissionsService.addBookSubmission(bookSubmission, image), "Failed to add the book");
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
