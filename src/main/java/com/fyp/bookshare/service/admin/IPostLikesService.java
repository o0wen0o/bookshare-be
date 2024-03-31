package com.fyp.bookshare.service.admin;

import com.fyp.bookshare.pojo.PostLikes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IPostLikesService extends IService<PostLikes> {

    boolean unlikePost(Integer userId, Integer postId);
}
