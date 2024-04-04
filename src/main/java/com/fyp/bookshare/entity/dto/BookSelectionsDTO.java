package com.fyp.bookshare.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * use for book selections for create post page
 * and posts edit-add page
 * and recommended books
 *
 * @author o0wen0o
 * @create 2024-04-01 12:25 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSelectionsDTO {

    private Integer id;

    private String title;

    private String imgUrl;
}
