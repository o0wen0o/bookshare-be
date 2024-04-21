package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.service.admin.IBooksService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author o0wen0o
 * @create 2024-03-31 5:01 PM
 */
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Resource
    IBooksService booksService;

    @GetMapping("/getBooks")
    @Operation(summary = "Get a list of books")
    public RestBean<IPage<Books>> getBooks(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "10"));
        String filter = params.getOrDefault("filter", "");

        Page<Books> page = new Page<>(current, size);
        IPage<Books> books = booksService.getBooks(page, filter);
        return RestBean.success(books);
    }
}