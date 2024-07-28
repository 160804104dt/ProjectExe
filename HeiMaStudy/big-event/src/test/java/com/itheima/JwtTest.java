package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtTest
 * 
 * @author dingtao
 * @date 2024/7/26 15:58
 */
public class JwtTest {

    //生成token
    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        String token = JWT.create()
                            .withClaim("user", claims)//添加载荷
                            .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*24))//添加过期时间
                            .sign(Algorithm.HMAC256("itheima"));//指定算法，配置密钥

        System.out.println(token);
    }

    //验证
    @Test
    public void testParse(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9."+
                       "eyJleHAiOjE3MjIwNjg2OTksInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoi5byg5LiJIn19."+
                       "TXF2Mxn6D2k-LzIPgpcUrFNp1eRMMcxYRsfSqqRSYjM";
        JWTVerifier itheima = JWT.require(Algorithm.HMAC256("itheima")).build();
        DecodedJWT verify = itheima.verify(token);
        Map<String, Claim> claims = verify.getClaims();
        System.out.println(claims.get("user"));
    }
}