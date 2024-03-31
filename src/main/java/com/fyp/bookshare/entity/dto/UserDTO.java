package com.fyp.bookshare.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fyp.bookshare.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * return value for user controller
 *
 * @author o0wen0o
 * @create 2024-03-19 12:45 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements BaseData {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private Boolean bookshelfVisible;

    private Boolean reviewVisible;

    private Boolean contributionVisible;

    private String avatar;
}
