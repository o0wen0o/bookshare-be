package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.BookSubmissions;
import com.fyp.bookshare.mapper.admin.BookSubmissionsMapper;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IBookSubmissionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.admin.IBooksService;
import com.fyp.bookshare.service.impl.OssServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class BookSubmissionsServiceImpl extends ServiceImpl<BookSubmissionsMapper, BookSubmissions> implements IBookSubmissionsService {

    @Resource
    private BookSubmissionsMapper bookSubmissionsMapper;

    @Resource
    private OssServiceImpl ossService;

    @Resource
    private IBooksService booksService;

    @Override
    public IPage<BookSubmissions> getBookSubmissions(Page<BookSubmissions> page, String filter) {
        QueryWrapper<BookSubmissions> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("title", filter)
                    .or().like("author", filter)
                    .or().like("publisher", filter)
                    .or().like("isbn", filter)
                    .or().like("publication_date", filter)
                    .or().like("language", filter)
                    .or().like("created_date", filter)
                    .or().like("status", filter);
        }

        return bookSubmissionsMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional
    public Boolean addBookSubmission(BookSubmissions bookSubmission, MultipartFile image) {
        // Save the bookSubmission without the image first to generate the book ID
        bookSubmission.setStatus("Pending");
        boolean isSave = this.save(bookSubmission);

        if (isSave && image != null && !image.isEmpty()) {
            String imageUrl = "bookSubmissions/image/" + ossService.generateFileName(bookSubmission.getId(), image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss

            bookSubmission.setImgUrl(imageUrl);
            return this.updateById(bookSubmission);
        }

        return isSave;
    }

    @Override
    @Transactional
    public Boolean acceptBookSubmission(Integer bookSubmissionId) {
        BookSubmissions bookSubmission = this.getById(bookSubmissionId);

        if (bookSubmission == null) {
            return false;
        }

        // Update the bookSubmission status
        bookSubmission.setStatus("Accepted");
        boolean isUpdate = this.updateById(bookSubmission);

        // Save the book to the database
        Books book = bookSubmission.asViewObject(Books.class);
        book.setId(null);
        boolean isSave = booksService.save(book);

        return isUpdate && isSave;
    }

    @Override
    public Boolean rejectBookSubmission(Integer bookSubmissionId) {
        BookSubmissions bookSubmission = this.getById(bookSubmissionId);

        if (bookSubmission == null) {
            return false;
        }

        // Update the bookSubmission status
        bookSubmission.setStatus("Rejected");
        return this.updateById(bookSubmission);
    }
}
