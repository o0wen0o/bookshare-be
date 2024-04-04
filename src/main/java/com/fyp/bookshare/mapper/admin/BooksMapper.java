package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookDetailDTO;
import com.fyp.bookshare.entity.dto.BookSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface BooksMapper extends BaseMapper<Books> {

    IPage<BookSelectionsDTO> selectBookSelectionsWithPagination(Page<Books> page, @Param("filter") String filter);

    BookDetailDTO getBookDetail(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

    List<BookDetailDTO> getRecommendedBooks(@Param("bookId") Integer bookId);
}
