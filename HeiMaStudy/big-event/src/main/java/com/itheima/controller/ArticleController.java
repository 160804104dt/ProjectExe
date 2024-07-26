package com.itheima.controller;

import com.itheima.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<String> list(){
        return Result.error("kkkk");
    }
}
