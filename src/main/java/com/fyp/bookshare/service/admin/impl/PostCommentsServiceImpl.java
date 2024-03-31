package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fyp.bookshare.entity.dto.PostCommentsDTO;
import com.fyp.bookshare.pojo.PostComments;
import com.fyp.bookshare.mapper.admin.PostCommentsMapper;
import com.fyp.bookshare.service.admin.IPostCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class PostCommentsServiceImpl extends ServiceImpl<PostCommentsMapper, PostComments> implements IPostCommentsService {

    @Resource
    PostCommentsMapper postCommentsMapper;

    @Override
    public List<PostCommentsDTO> getPostCommentsDTO(Integer postId, Integer userId) {
        return postCommentsMapper.getPostCommentsDTO(postId, userId);
    }
}
