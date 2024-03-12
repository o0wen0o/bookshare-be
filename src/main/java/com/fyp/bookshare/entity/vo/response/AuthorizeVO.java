package com.fyp.bookshare.entity.vo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 登录验证成功的用户信息响应
 */
@Data
public class AuthorizeVO {

    private String username;

    private String email;

    private List<String> roles;

    private String token;

    private Date expire;
}
