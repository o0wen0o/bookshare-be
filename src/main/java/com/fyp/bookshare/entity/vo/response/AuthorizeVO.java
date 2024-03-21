package com.fyp.bookshare.entity.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 登录验证成功的用户信息响应
 */
@Data
public class AuthorizeVO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String email;

    private String avatar;

    private List<String> roles;

    private String token;

    private Date expire;
}
