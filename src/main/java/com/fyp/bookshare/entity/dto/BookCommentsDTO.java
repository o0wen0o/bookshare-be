package com.fyp.bookshare.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * book comments and replies in book detail page
 *
 * @author o0wen0o
 * @create 2024-04-04 4:09 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCommentsDTO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String avatar;

    private String text;

    private LocalDateTime createdDate;

    private Integer likes;

    private Boolean thumbed;

    private Integer replyCount;
}
