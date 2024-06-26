package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.entity.dto.BookDetailDTO;
import com.fyp.bookshare.entity.dto.BookSelectionsDTO;
import com.fyp.bookshare.mapper.admin.BooksMapper;
import com.fyp.bookshare.pojo.BookCommentLikes;
import com.fyp.bookshare.pojo.BookComments;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.service.admin.IBookRatingsService;
import com.fyp.bookshare.service.admin.IBooksService;
import com.fyp.bookshare.service.impl.OssServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements IBooksService {

    @Resource
    private BooksMapper booksMapper;

    @Resource
    private OssServiceImpl ossService;

    @Override
    public IPage<Books> getBooks(Page<Books> page, String filter) {
        QueryWrapper<Books> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("title", filter)
                    .or().like("author", filter)
                    .or().like("publisher", filter)
                    .or().like("isbn", filter)
                    .or().like("publication_date", filter)
                    .or().like("language", filter);
        }

        return booksMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<BookSelectionsDTO> getBookSelections(Page<Books> page, String filter) {
        return booksMapper.selectBookSelectionsWithPagination(page, filter);
    }

    @Override
    public BookDetailDTO getBookDetail(Integer bookId, Integer userId) {
        return booksMapper.getBookDetail(bookId, userId);
    }

    @Override
    public List<BookDetailDTO> getRecommendedBooks(Integer bookId) {
        return booksMapper.getRecommendedBooks(bookId);
    }

    @Override
    @Transactional
    public boolean addBook(Books book, MultipartFile image) {
        // Save the book without the image first to generate the book ID
        boolean isSave = this.save(book);

        if (isSave && image != null && !image.isEmpty()) {
            String imageUrl = "books/image/" + ossService.generateFileName(book.getId(), image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss

            book.setImgUrl(imageUrl);
            return this.updateById(book);
        }

        return isSave;
    }

    @Override
    @Transactional
    public boolean updateBook(Integer id, Books book, MultipartFile image) {
        book.setId(id);

        // Check if an image is provided
        if (image != null && !image.isEmpty()) {
            String imageUrl = "books/image/" + ossService.generateFileName(id, image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss
            book.setImgUrl(imageUrl);
        }

        return this.updateById(book);
    }

    @Override
    @Transactional
    public boolean incrementFavourite(Integer bookId) {
        UpdateWrapper<Books> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", bookId)
                .setSql("favourite = favourite + 1");

        return booksMapper.update(null, updateWrapper) > 0;
    }

    @Override
    @Transactional
    public boolean decrementFavourite(Integer bookId) {
        UpdateWrapper<Books> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", bookId)
                .setSql("favourite = favourite - 1");

        return booksMapper.update(null, updateWrapper) > 0;
    }
}
