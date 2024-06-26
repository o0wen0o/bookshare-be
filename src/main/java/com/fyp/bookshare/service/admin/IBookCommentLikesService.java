package com.fyp.bookshare.service.admin;

import com.fyp.bookshare.pojo.BookCommentLikes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IBookCommentLikesService extends IService<BookCommentLikes> {

    boolean likePostComment(Integer bookCommentId, Integer userId);

    boolean unlikePostComment(Integer bookCommentId, Integer userId);
}
