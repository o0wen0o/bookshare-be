package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Bookshelves;
import com.fyp.bookshare.mapper.admin.BookshelvesMapper;
import com.fyp.bookshare.service.admin.IBookshelvesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
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
public class BookshelvesServiceImpl extends ServiceImpl<BookshelvesMapper, Bookshelves> implements IBookshelvesService {

    @Resource
    private BookshelvesMapper bookshelvesMapper;

    @Override
    public IPage<Books> getFavouriteBooks(Page<Books> page, Integer userId) {
        return bookshelvesMapper.getFavouriteBooks(page, userId);
    }
}
