package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fyp.bookshare.entity.dto.PostsDTO;
import com.fyp.bookshare.pojo.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface PostsMapper extends BaseMapper<Posts> {
    IPage<PostsDTO> getPostsDTO(@Param("page") Page<PostsDTO> page, @Param("userId") Integer userId);
}
