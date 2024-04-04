package com.fyp.bookshare.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Receive userId and bookId
 *
 * @author o0wen0o
 * @create 2024-04-04 10:27 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFavouriteDTO {

    private Integer bookId;

    private Integer userId;
}
