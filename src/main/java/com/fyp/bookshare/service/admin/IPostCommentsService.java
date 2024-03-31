package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fyp.bookshare.entity.dto.PostCommentsDTO;
import com.fyp.bookshare.pojo.PostComments;
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
public interface IPostCommentsService extends IService<PostComments> {

    List<PostCommentsDTO> getPostCommentsDTO(Integer postId, Integer userId);

    boolean incrementLikes(Integer postCommentId);

    boolean decrementLikes(Integer postCommentId);
}
