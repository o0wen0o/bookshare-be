package com.fyp.bookshare.service.admin;

import com.fyp.bookshare.pojo.BookRatings;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IBookRatingsService extends IService<BookRatings> {

    boolean rateBook(BookRatings bookRating);

    Double calculateAverageRating(Integer bookId);
}
