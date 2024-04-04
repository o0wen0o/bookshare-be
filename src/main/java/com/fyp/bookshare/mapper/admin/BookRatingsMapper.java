package com.fyp.bookshare.mapper.admin;

import com.fyp.bookshare.pojo.BookRatings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface BookRatingsMapper extends BaseMapper<BookRatings> {

    Double calculateAverageRating(@Param("bookId") Integer bookId);
}
