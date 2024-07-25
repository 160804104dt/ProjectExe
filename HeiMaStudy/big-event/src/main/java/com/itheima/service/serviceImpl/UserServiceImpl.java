package com.itheima.service.serviceImpl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 * 
 * @author dingtao
 * @date 2024/7/25 16:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public void register(String userName, String password) {
         //加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(userName,md5String);
    }
}
