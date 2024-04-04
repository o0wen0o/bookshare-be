package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyp.bookshare.pojo.*;
import com.fyp.bookshare.mapper.admin.BookshelfPivotBooksMapper;
import com.fyp.bookshare.service.admin.IBooksService;
import com.fyp.bookshare.service.admin.IBookshelfPivotBooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.admin.IBookshelvesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class BookshelfPivotBooksServiceImpl extends ServiceImpl<BookshelfPivotBooksMapper, BookshelfPivotBooks> implements IBookshelfPivotBooksService {

    @Resource
    private IBookshelvesService bookshelvesService;

    @Resource
    private IBooksService booksService;

    @Resource
    private BookshelfPivotBooksMapper bookshelfPivotBooksMapper;

    @Override
    @Transactional
    public boolean addToBookshelf(Integer bookId, Integer userId) {
        // Step 1: Get the bookshelfId by userId
        QueryWrapper<Bookshelves> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.select("id");
        Bookshelves bookshelf = bookshelvesService.getOne(queryWrapper);
        Integer bookshelfId = bookshelf.getId();

        // Step 2: Create a new favorite record
        boolean save = this.save(new BookshelfPivotBooks(null, bookshelfId, bookId));

        if (save) {
            // Step 3: Increment favorite count in book
            return booksService.incrementFavourite(bookId);
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteFromBookshelf(Integer bookId, Integer userId) {
        // Step 1: Get the bookshelfId by userId
        QueryWrapper<Bookshelves> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.select("id");
        Bookshelves bookshelf = bookshelvesService.getOne(queryWrapper);
        Integer bookshelfId = bookshelf.getId();

        // Step 2: Delete the favorite record
        QueryWrapper<BookshelfPivotBooks> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("book_id", bookId).eq("bookshelf_id", bookshelfId);
        int deleteCount = bookshelfPivotBooksMapper.delete(deleteWrapper);

        if (deleteCount > 0) {
            // Step 3: Decrement favorite count in posts
            return booksService.decrementFavourite(bookId);
        }

        return false;
    }
}
