package com.itheima.springbootmybatis;

import com.itheima.springbootmybatis.mapper.UserMapper;
import com.itheima.springbootmybatis.pojo.User;
import com.itheima.springbootmybatis.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 * 
 * @author dingtao
 * @date 2024/7/24 14:56
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
