package com.fyp.bookshare.service.admin;

import com.fyp.bookshare.pojo.BookshelfPivotBooks;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IBookshelfPivotBooksService extends IService<BookshelfPivotBooks> {

    boolean addToBookshelf(Integer bookId, Integer userId);

    boolean deleteFromBookshelf(Integer bookId, Integer userId);
}
