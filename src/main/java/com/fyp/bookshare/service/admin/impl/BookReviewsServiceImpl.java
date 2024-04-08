package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookReviewDTO;
import com.fyp.bookshare.pojo.BookReviews;
import com.fyp.bookshare.mapper.admin.BookReviewsMapper;
import com.fyp.bookshare.service.admin.IBookReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class BookReviewsServiceImpl extends ServiceImpl<BookReviewsMapper, BookReviews> implements IBookReviewsService {

    @Resource
    private BookReviewsMapper bookReviewsMapper;


    @Override
    public IPage<BookReviewDTO> getBookReviewsByUserId(Page<BookReviewDTO> page, Integer userId) {
        return bookReviewsMapper.getBookReviewsByUserId(page, userId);
    }

    @Override
    public BookReviewDTO getBookReviewById(Integer id) {
        return bookReviewsMapper.getBookReviewById(id);
    }

    @Override
    public boolean createBookReview(BookReviews bookReview) {
        return bookReviewsMapper.insert(bookReview) > 0;
    }

    @Override
    public boolean updateBookReview(BookReviews bookReview) {
        return bookReviewsMapper.updateById(bookReview) > 0;
    }
}
