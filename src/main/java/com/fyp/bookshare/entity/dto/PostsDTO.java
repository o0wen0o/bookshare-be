package com.fyp.bookshare.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * community posts for client side
 *
 * @author o0wen0o
 * @create 2024-03-31 10:29 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsDTO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId; // who created this post

    private String username;

    private String avatar;

    private String content;

    private LocalDateTime createdDate;

    private Integer likes;

    private Boolean thumbed;

    private Integer shares;

    private Integer commentCount;
}
