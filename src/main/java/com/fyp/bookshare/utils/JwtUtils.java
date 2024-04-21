package com.fyp.bookshare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用于处理Jwt令牌的工具类
 */
@Component
public class JwtUtils {

    //用于给Jwt令牌签名校验的秘钥
    @Value("${spring.security.jwt.key}")
    private String key;

    //令牌的过期时间，以小时为单位
    @Value("${spring.security.jwt.expire}")
    private int expire;

    //为用户生成Jwt令牌的冷却时间，防止刷接口频繁登录生成令牌，以秒为单位
    @Value("${spring.security.jwt.limit.base}")
    private int limit_base;

    //用户如果继续恶意刷令牌，更严厉的封禁时间
    @Value("${spring.security.jwt.limit.upgrade}")
    private int limit_upgrade;

    //判定用户在冷却时间内，继续恶意刷令牌的次数
    @Value("${spring.security.jwt.limit.frequency}")
    private int limit_frequency;

    @Resource
    StringRedisTemplate template;

    @Resource
    FlowUtils utils;

    /**
     * 让指定Jwt令牌失效
     *
     * @param headerToken 请求头中携带的令牌
     * @return 是否操作成功
     */
    public boolean invalidateJwt(String headerToken) {
        String token = this.convertToken(headerToken);
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            return deleteToken(verify.getId(), verify.getExpiresAt());

        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 根据配置快速计算过期时间
     *
     * @return 过期时间
     */
    public Date expireTime() {
        Calendar calendar = Calendar.getInstance(); // current date and time
        calendar.add(Calendar.HOUR, expire);
        return calendar.getTime();
    }

    /**
     * Generate Jwt token based on UserDetails
     *
     * @param user user information
     * @return token
     */
    public String createJwt(UserDetails user, String email, int userId, Date expire) {
        if (this.frequencyCheck(userId)) {
            Algorithm algorithm = Algorithm.HMAC256(key);

            // store authorities in JWT for Spring Security
            List<String> authorities = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority) // ROLE_[User, Admin]
                    .map(JwtUtils::extractRoles)
                    .flatMap(List::stream)
                    .map(role -> "ROLE_" + role)
                    .collect(Collectors.toList());

            return JWT.create()
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("id", userId)
                    .withClaim("email", email)
                    .withClaim("authorities", authorities)
                    .withExpiresAt(expire)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } else {
            return null;
        }
    }

    /**
     * 解析Jwt令牌
     *
     * @param headerToken 请求头中携带的令牌
     * @return DecodedJWT
     */
    public DecodedJWT validateJwt(String headerToken) {
        String token = this.convertToken(headerToken);

        if (token == null)
            return null;

        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        try {
            DecodedJWT verify = jwtVerifier.verify(token);

            // 校验Jwt令牌是否被列入黑名单
            if (this.isInvalidToken(verify.getId()))
                return null;

            Map<String, Claim> claims = verify.getClaims();
            return new Date().after(claims.get("exp").asDate()) ? null : verify;

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 将jwt对象中的内容封装为UserDetails
     *
     * @param jwt 已解析的Jwt对象
     * @return UserDetails
     */
    public UserDetails toUserDetails(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("email").asString()) // changed name to email
                .password("******")
                .authorities(claims.get("authorities").asArray(String.class))
                .build();
    }

    /**
     * 将jwt对象中的用户ID提取出来
     *
     * @param jwt 已解析的Jwt对象
     * @return 用户ID
     */
    public Integer toId(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("id").asInt();
    }

    /**
     * 频率检测，防止用户高频申请Jwt令牌，并且采用阶段封禁机制
     * 如果已经提示无法登录的情况下用户还在刷，那么就封禁更长时间
     *
     * @param userId 用户ID
     * @return 是否通过频率检测
     */
    private boolean frequencyCheck(int userId) {
        String key = Const.JWT_FREQUENCY + userId;
        return utils.limitOnceUpgradeCheck(key, limit_frequency, limit_base, limit_upgrade);
    }

    /**
     * 校验并转换请求头中的Token令牌
     *
     * @param headerToken 请求头中的Token
     * @return 转换后的令牌
     */
    private String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }

    /**
     * 将Token列入Redis黑名单中
     *
     * @param uuid 令牌ID
     * @param time 过期时间
     * @return 是否操作成功
     */
    private boolean deleteToken(String uuid, Date time) {
        if (this.isInvalidToken(uuid))
            return false;
        Date now = new Date();
        long expire = Math.max(time.getTime() - now.getTime(), 0);
        template.opsForValue().set(Const.JWT_BLACK_LIST + uuid, "", expire, TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * 验证Token是否被列入Redis黑名单
     *
     * @param uuid 令牌ID
     * @return 是否操作成功
     */
    private boolean isInvalidToken(String uuid) {
        return Boolean.TRUE.equals(template.hasKey(Const.JWT_BLACK_LIST + uuid));
    }

    /**
     * 从角色字符串中提取角色列表
     *
     * @param authority
     * @return
     */
    private static List<String> extractRoles(String authority) {
        Pattern pattern = Pattern.compile("ROLE_\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(authority);

        if (matcher.find()) {
            String roles = matcher.group(1);
            return List.of(roles.split(", "));
        }

        return List.of();
    }
}
