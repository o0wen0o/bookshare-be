package com.fyp.bookshare.entity.dto;

import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Genres;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author o0wen0o
 * @create 2024-04-04 11:44 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailDTO extends Books {

    private String genres;

    private Boolean isFavourite;

    private Integer commentCount;

    private Integer ratingCount;
}
