package com.fyp.bookshare.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Getter
@Setter
@TableName("book_comment_likes")
@ApiModel(value = "BookCommentLikes对象", description = "")
public class BookCommentLikes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("book_comment_id")
    private Integer bookCommentId;

    @TableField("user_id")
    private Integer userId;
}
