package com.fyp.bookshare.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * community posts' comments
 *
 * @author o0wen0o
 * @create 2024-04-01 12:02 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentsDTO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String username;

    private String avatar;

    private String text;

    private LocalDateTime createdDate;

    private Integer likes;

    private Boolean thumbed;
}
