package com.fyp.bookshare.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fyp.bookshare.entity.BaseData;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("books")
@ApiModel(value = "Books对象", description = "")
public class Books implements Serializable, BaseData {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("author")
    private String author;

    @TableField("description")
    private String description;

    @TableField("publisher")
    private String publisher;

    @TableField("isbn")
    private String isbn;

    @TableField("publication_date")
    private LocalDate publicationDate;

    @TableField("page")
    private Integer page;

    @TableField("language")
    private String language;

    @TableField("img_url")
    private String imgUrl;

    @TableField("rating")
    private BigDecimal rating;

    @TableField("favourite")
    private Integer favourite;

    @TableField("review")
    private Integer review;
}
