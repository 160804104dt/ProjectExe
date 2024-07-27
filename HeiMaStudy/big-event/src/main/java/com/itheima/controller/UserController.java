package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User user = userService.findByUserName(username);
        if (user == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        //判断用户是否存在
        if (loginUser == null) {
            return Result.error("用户不存在");
        }
        //判断密码是否正确，注意加密
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        try {
            //根据用户名查询用户
            Map<String, Object> claims = ThreadLocalUtil.get();
            User user = userService.findByUserName((String) claims.get("username"));
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("获取用户详细信息异常");
        }
    }

    /**
     * 更新用户基本信息
     *
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    /**
     * 更新用户头像
     *
     * @param avatarUrl
     * @return
     */

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @NotEmpty @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 更新密码
     *
     * @param pwds
     * @return
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> pwds) {
        String oldPwd = pwds.get("old_pwd");
        String newPwd = pwds.get("new_pwd");

        //判断密码是否为空
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd)){
            return Result.error("密码不能为空");
        }
        //判断原密码是否正确,数据库中的密码是加密过的
        Map<String, Object> map = ThreadLocalUtil.get();
        String userName = (String) map.get("username");
        User loginUser = userService.findByUserName(userName);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码输入不正确");
        }
        //更新密码，注意需要加密
        userService.updatePwd(Md5Util.getMD5String(newPwd),loginUser.getId());
        return Result.success();
    }
}
