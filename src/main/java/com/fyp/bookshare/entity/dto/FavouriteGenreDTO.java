package com.fyp.bookshare.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for receiving favourite genre
 *
 * @author o0wen0o
 * @create 2024-04-08 10:04 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteGenreDTO {

    private List<Integer> ids;

    private Integer userId;
}
