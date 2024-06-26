package com.fyp.bookshare.config;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.UserLoginDTO;
import com.fyp.bookshare.entity.vo.response.AuthorizeVO;
import com.fyp.bookshare.filter.JwtAuthenticationFilter;
import com.fyp.bookshare.filter.RequestLogFilter;
import com.fyp.bookshare.service.AccountService;
import com.fyp.bookshare.utils.Const;
import com.fyp.bookshare.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * SpringSecurity related configuration
 */
@Configuration
public class SecurityConfiguration {

    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    RequestLogFilter requestLogFilter;

    @Resource
    JwtUtils utils;

    @Resource
    AccountService service;

    /**
     * New configuration method for SpringSecurity 6
     *
     * @param http configurator
     * @return automatically constructed built-in filter chain
     * @throws Exception possible exceptions
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/api/auth/**", "/error").permitAll()
                        .requestMatchers("/api/stripe-webhook/**").permitAll()
                        .requestMatchers(SecurityPathConfig.ADMIN_PATHS).hasRole(Const.ROLE_ADMIN)
                        .anyRequest().hasAnyRole(Const.ROLE_DEFAULT)
                )
                .formLogin(conf -> conf
                        .loginProcessingUrl("/api/auth/login")
                        .failureHandler(this::handleProcess)
                        .successHandler(this::handleProcess)
                        .permitAll()
                )
                .logout(conf -> conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .exceptionHandling(conf -> conf
                        .accessDeniedHandler(this::handleProcess)
                        .authenticationEntryPoint(this::handleProcess)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(requestLogFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, RequestLogFilter.class)
                .build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*"); // Or specify: GET, POST, etc.
        configuration.addAllowedHeader("*"); // Or specify: Content-Type, Authorization, etc.
        configuration.setAllowCredentials(true); // If you need cookies/header/credentials
        configuration.addExposedHeader("Authorization"); // If you're using tokens in headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all paths
        return source;
    }

    /**
     * Integrate multiple types of Handlers into the same method, including:
     * - login successful
     * - Login failed
     * - Interception without login/interception without permission
     *
     * @param request
     * @param response
     * @param exceptionOrAuthentication exception or verification entity
     * @throws IOException possible exception
     */
    private void handleProcess(HttpServletRequest request,
                               HttpServletResponse response,
                               Object exceptionOrAuthentication) throws IOException {

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();

        if (exceptionOrAuthentication instanceof AccessDeniedException exception) {
            writer.write(RestBean
                    .forbidden(exception.getMessage() + ", please contact the administrator").asJsonString());

        } else if (exceptionOrAuthentication instanceof Exception exception) {
            writer.write(RestBean
                    .unauthorized(exception.getMessage()).asJsonString());
            // .unauthorized("Incorrect email or password!").asJsonString());

        } else if (exceptionOrAuthentication instanceof Authentication authentication) {
            User user = (User) authentication.getPrincipal();

            UserLoginDTO userLoginDTO = service.getUserByEmail(user.getUsername()); // getUsername is get an email
            Date expire = utils.expireTime();
            String jwt = utils.createJwt(user, userLoginDTO.getEmail(), userLoginDTO.getId(), expire);

            if (jwt == null) {
                writer.write(RestBean.forbidden("Frequent requests, please try again later").asJsonString());

            } else {
                AuthorizeVO vo = userLoginDTO.asViewObject(AuthorizeVO.class, v -> {
                    v.setExpire(expire);
                    v.setToken(jwt);
                });
                writer.write(RestBean.success(vo).asJsonString());
            }
        }
    }

    /**
     * 退出登录处理，将对应的Jwt令牌列入黑名单不再使用
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 验证实体
     * @throws IOException 可能的异常
     */
    private void onLogoutSuccess(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Authentication authentication) throws IOException {

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String authorization = request.getHeader("Authorization");

        if (utils.invalidateJwt(authorization)) {
            writer.write(RestBean.success("Logout successfully").asJsonString());
            return;
        }

        writer.write(RestBean.failure(400, "Logout failed").asJsonString());
    }
}
