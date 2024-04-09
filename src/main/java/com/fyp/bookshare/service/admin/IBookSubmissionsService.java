package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.BookSubmissions;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IBookSubmissionsService extends IService<BookSubmissions> {

    IPage<BookSubmissions> getBookSubmissions(Page<BookSubmissions> page, String filter);

    Boolean addBookSubmission(BookSubmissions bookSubmission, MultipartFile image);

    Boolean acceptBookSubmission(Integer bookSubmissionId);

    Boolean rejectBookSubmission(Integer bookSubmissionId);
}
