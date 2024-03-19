package com.fyp.bookshare.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fyp.bookshare.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 数据库中的用户信息
 * use for login
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO implements BaseData {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Date createdDate;

    private List<String> roles;
}