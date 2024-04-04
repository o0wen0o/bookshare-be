package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookCommentsDTO;
import com.fyp.bookshare.pojo.BookComments;
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
public interface BookCommentsMapper extends BaseMapper<BookComments> {

    IPage<BookCommentsDTO> getCommentsDTO(@Param("page") Page<BookCommentsDTO> page, @Param("bookId") Integer bookId, @Param("userId") Integer userId);

    List<BookCommentsDTO> getCommentReplies(@Param("bookCommentId") Integer bookCommentId, @Param("userId") Integer userId);
}
