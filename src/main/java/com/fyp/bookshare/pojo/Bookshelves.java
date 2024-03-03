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
@TableName("bookshelves")
@ApiModel(value = "Bookshelves对象", description = "")
public class Bookshelves implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("bookshelf_name")
    private String bookshelfName;

    @TableField("user_id")
    private Integer userId;
}
