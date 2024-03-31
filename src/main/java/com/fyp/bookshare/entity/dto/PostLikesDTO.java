package com.fyp.bookshare.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * to like a post
 *
 * @author o0wen0o
 * @create 2024-04-01 3:14 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikesDTO {

    private String postId;

    private String userId;
}
