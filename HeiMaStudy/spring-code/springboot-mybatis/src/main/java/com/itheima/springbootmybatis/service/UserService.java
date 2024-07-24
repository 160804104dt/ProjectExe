package com.itheima.springbootmybatis.service;

import com.itheima.springbootmybatis.pojo.User;

/**
 * UserService
 *
 * @author dingtao
 * @date 2024/7/24 14:55
 */
public interface UserService {

    User findById(Integer id);
}
