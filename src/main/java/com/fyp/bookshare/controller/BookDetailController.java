package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.BookDetailDTO;
import com.fyp.bookshare.service.admin.IBooksService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getBookDetail/{bookId}/{userId}")
    @Operation(summary = "Get book detail")
    public RestBean<BookDetailDTO> getBookDetail(@PathVariable Integer bookId, @PathVariable Integer userId) {
        BookDetailDTO bookDetailDTO = booksService.getBookDetail(bookId, userId);
        return RestBean.success(bookDetailDTO);
    }
}
