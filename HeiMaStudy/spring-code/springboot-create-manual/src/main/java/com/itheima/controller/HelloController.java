package com.itheima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 * 
 * @author dingtao
 * @date 2024/7/24 13:55
 */
@RestController
@ConfigurationProperties(prefix = "user")
public class HelloController {

    @Value("${user.username}")
    private String userName;

    @RequestMapping("/hello")
    public String Hello(){
        return userName;
    }
}
