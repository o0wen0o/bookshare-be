package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookCommentsDTO;
import com.fyp.bookshare.pojo.BookComments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IBookCommentsService extends IService<BookComments> {

    IPage<BookComments> getBookCommentsByBookId(Page<BookComments> page, Integer bookId, String filter);

    IPage<BookCommentsDTO> getCommentsDTO(Page<BookCommentsDTO> page, Integer bookId, Integer userId);

    List<BookCommentsDTO> getCommentReplies(Integer bookCommentId, Integer userId);

    Integer createBookComment(BookComments bookComments);

    boolean incrementLikes(Integer bookCommentId);

    boolean decrementLikes(Integer bookCommentId);
}
