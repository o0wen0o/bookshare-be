package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Bookshelves;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface BookshelvesMapper extends BaseMapper<Bookshelves> {

    IPage<Books> getFavouriteBooks(Page<Books> page, Integer userId);
}
