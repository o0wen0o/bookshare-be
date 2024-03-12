package com.fyp.bookshare.config;

/**
 * SpringSecurity路径相关配置
 *
 * @author o0wen0o
 * @create 2024-03-09 10:11 AM
 */
public interface SecurityPathConfig {


    String[] ADMIN_PATHS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/users/**",
            "/api/roles/**"
    };
}
