package com.qfggk.server.config.security.component;


import com.sun.org.apache.regexp.internal.RE;
import io.jsonwebtoken.*;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: WJQ
 * @date 2021-06-13 15:49
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 根据用户信息生成Token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails)
    {
        Map<String ,Object> claims =new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);


    }

    /**
     * 从登陆token中获取登录用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token)
    {
        String username;
        try {
            Claims claims=getClaimsFromToken(token);
            username=claims.getSubject();
        } catch (Exception e) {
            username=null;
        }
        return  username;

    }

    /**
     * 验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails)
    {
        String username=getUserNameFromToken(token);
        return username.equals(userDetails.getUsername())&&!isTokenExpired(token);

    }

    /**
     * 判断token是否失效，失效返回true
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token)
    {
       Date expiredDate = getExpiredDateFromToken(token);
       return expiredDate.before(new Date());
    }

    /**
     * 判断token是否可以被刷新，过期了就可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token)
    {
        return !isTokenExpired(token);
    }

    /**
     * 从token获取失效时间
     * @param token
     * @return
     */
    public Date getExpiredDateFromToken(String token)
    {
        Claims claims=getClaimsFromToken(token);
        Date date = claims.getExpiration();
        return date;
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token)
    {
        Claims claims=getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取负载
     * @param token
     * @return
     */
    public Claims  getClaimsFromToken(String token)
    {
        Claims claims=null;
        try {
            claims =Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据负载生成Token
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims)
    {

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 生成token失效时间
     * @return
     */
    private Date generateExpirationDate()
    {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
