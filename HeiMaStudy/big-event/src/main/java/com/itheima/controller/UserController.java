package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author dingtao
 * @date 2024/7/25 16:10
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String userName, @Pattern(regexp = "^\\S{5,16}$")String password) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            userService.register(userName,password);
            return Result.success();
        }
        else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        //判断用户是否存在
        if(loginUser == null){
            return Result.error("用户不存在");
        }
        //判断密码是否正确，注意加密
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            return Result.success("jwt token");
        }
        return  Result.error("密码错误");
    }
}
