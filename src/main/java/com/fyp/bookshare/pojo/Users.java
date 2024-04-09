package com.fyp.bookshare.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fyp.bookshare.entity.BaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("users")
@ApiModel(value = "Users对象", description = "")
public class Users implements Serializable, BaseData {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("email")
    private String email;

    @TableField("password")
    private String password;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("is_bookshelf_visible")
    private Boolean bookshelfVisible;

    @TableField("is_review_visible")
    private Boolean reviewVisible;

    @TableField("is_contribution_visible")
    private Boolean contributionVisible;

    @TableField("created_date")
    private LocalDateTime createdDate;

    @TableField("avatar")
    private String avatar;
}
