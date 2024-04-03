package com.fyp.bookshare.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * edit-add page of posts for admin panel
 *
 * @author o0wen0o
 * @create 2024-03-31 10:29 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsEditDTO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    private Integer userId;

    private Integer bookId;

}
