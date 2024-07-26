package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ArticleController
 * 
 * @author dingtao
 * @date 2024/7/26 15:17
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public Result<String> list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response){

        try {
            //验证token
            Map<String, Object> claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("未登录");
        }

        return Result.success("所有的文章数据");
    }
}
