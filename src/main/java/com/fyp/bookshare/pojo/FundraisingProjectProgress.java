package com.fyp.bookshare.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author o0wen0o
 * @since 2024-04-11
 */
@Getter
@Setter
@TableName("fundraising_project_progress")
@ApiModel(value = "FundraisingProjectProgress对象", description = "")
public class FundraisingProjectProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("img_url")
    private String imgUrl;

    @TableField("updated_date")
    private LocalDateTime updatedDate;

    @TableField("created_date")
    private LocalDateTime createdDate;

    @TableField("fundraising_project_id")
    private Integer fundraisingProjectId;
}
