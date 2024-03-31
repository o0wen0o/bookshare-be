package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fyp.bookshare.entity.dto.PostCommentsDTO;
import com.fyp.bookshare.pojo.PostComments;
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
public interface PostCommentsMapper extends BaseMapper<PostComments> {

    List<PostCommentsDTO> getPostCommentsDTO(@Param("postId") Integer postId, @Param("userId") Integer userId);
}
