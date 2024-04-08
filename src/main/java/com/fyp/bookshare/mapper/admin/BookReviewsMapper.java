package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookReviewDTO;
import com.fyp.bookshare.pojo.BookReviews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface BookReviewsMapper extends BaseMapper<BookReviews> {

    IPage<BookReviewDTO> getBookReviewsByUserId(@Param("page") Page<BookReviewDTO> page, @Param("userId") Integer userId);

    BookReviewDTO getBookReviewById(@Param("id") Integer id);
}
