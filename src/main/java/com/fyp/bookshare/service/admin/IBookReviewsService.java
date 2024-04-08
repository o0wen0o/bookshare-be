package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookReviewDTO;
import com.fyp.bookshare.pojo.BookReviews;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IBookReviewsService extends IService<BookReviews> {

    IPage<BookReviewDTO> getBookReviewsByUserId(Page<BookReviewDTO> page, Integer userId);

    BookReviewDTO getBookReviewById(Integer id);

    boolean createBookReview(BookReviews bookReview);

    boolean updateBookReview(BookReviews bookReview);
}
