package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.BookDetailDTO;
import com.fyp.bookshare.entity.dto.BookSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IBooksService extends IService<Books> {

    IPage<Books> getBooks(Page<Books> page, String filter);

    IPage<BookSelectionsDTO> getBookSelections(Page<Books> page, String filter);

    BookDetailDTO getBookDetail(Integer bookId, Integer userId);

    List<BookDetailDTO> getRecommendedBooks(Integer bookId);

    boolean addBook(Books book, MultipartFile image);

    boolean updateBook(Integer id, Books book, MultipartFile image);
}
