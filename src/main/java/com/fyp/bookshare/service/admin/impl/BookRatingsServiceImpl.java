package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fyp.bookshare.mapper.admin.BooksMapper;
import com.fyp.bookshare.pojo.BookRatings;
import com.fyp.bookshare.mapper.admin.BookRatingsMapper;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.BookshelfPivotBooks;
import com.fyp.bookshare.service.admin.IBookRatingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.admin.IBooksService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class BookRatingsServiceImpl extends ServiceImpl<BookRatingsMapper, BookRatings> implements IBookRatingsService {

    @Resource
    BookRatingsMapper bookRatingsMapper;

    @Resource
    BooksMapper booksMapper;

    @Override
    @Transactional
    public boolean rateBook(BookRatings bookRating) {
        Integer bookId = bookRating.getBookId();
        Integer userId = bookRating.getUserId();
        BigDecimal rating = bookRating.getRating();

        // Step 1: Check if a rating already exists
        QueryWrapper<BookRatings> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId).eq("user_id", userId);
        BookRatings existingRating = bookRatingsMapper.selectOne(queryWrapper);

        // Step 2: Update or insert the rating
        if (existingRating != null) {
            // Update existing rating
            existingRating.setRating(rating);
            bookRatingsMapper.updateById(existingRating);
        } else {
            // Insert new rating
            bookRatingsMapper.insert(bookRating);
        }

        // Step 3: Update the average rating in the Books table
        Double averageRating = calculateAverageRating(bookId);
        if (averageRating != null) {
            UpdateWrapper<Books> bookUpdateWrapper = new UpdateWrapper<>();
            bookUpdateWrapper.eq("id", bookId).set("rating", averageRating);
            return booksMapper.update(null, bookUpdateWrapper) > 0;
        }

        return false;
    }

    @Override
    public Double calculateAverageRating(Integer bookId) {
        return bookRatingsMapper.calculateAverageRating(bookId);
    }
}
