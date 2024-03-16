package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IBooksService;
import com.fyp.bookshare.service.admin.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Resource
    IBooksService booksService;

    @GetMapping("/")
    @Operation(summary = "Get a list of books")
    public RestBean<IPage<Books>> getBooks(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Books> page = new Page<>(current, size);
        IPage<Books> books = booksService.getBooks(page, filter);
        return RestBean.success(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by its ID")
    public RestBean<Books> getBookById(@PathVariable Long id) {
        Books book = booksService.getById(id);
        if (book != null) {
            return RestBean.success(book);
        } else {
            return RestBean.failure(400, "Book not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new book")
    public RestBean<Void> addBook(@RequestBody Books book) {
        return messageHandle(() -> booksService.save(book), "Failed to add the book");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing book")
    public RestBean<Void> updateBook(@PathVariable Integer id, @RequestBody Books book) {
        book.setId(id);
        return messageHandle(() -> booksService.updateById(book), "Failed to update the book");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a book")
    public RestBean<Void> deleteBooks(@PathVariable List<Integer> ids) {
        return messageHandle(() -> booksService.removeByIds(ids), "Failed to delete the book");
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
