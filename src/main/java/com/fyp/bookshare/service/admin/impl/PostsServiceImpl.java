package com.fyp.bookshare.service.admin.impl;

import com.fyp.bookshare.pojo.Posts;
import com.fyp.bookshare.mapper.admin.PostsMapper;
import com.fyp.bookshare.service.admin.IPostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

}
