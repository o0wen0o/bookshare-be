package com.fyp.bookshare.entity.dto;

import com.fyp.bookshare.pojo.BookReviews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * use for get all book reviews
 * and get book review when edit-add
 *
 * @author o0wen0o
 * @create 2024-04-08 2:48 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReviewDTO extends BookReviews {

    private String title;

    private String imgUrl;
}
