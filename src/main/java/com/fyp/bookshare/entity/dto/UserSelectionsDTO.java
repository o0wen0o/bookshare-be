package com.fyp.bookshare.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * use for user selections for posts edit-add page
 *
 * @author o0wen0o
 * @create 2024-04-03 09:35 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSelectionsDTO {

    private Integer id;

    private String username;
}
