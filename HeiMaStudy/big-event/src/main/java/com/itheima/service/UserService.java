package com.itheima.service;

import com.itheima.pojo.User;

/**
 * UserService
 *
 * @author dingtao
 * @date 2024/7/25 16:11
 */
public interface UserService {
    User findByUserName(String userName);

    //注册
    void register(String userName, String password);

    //更新
    void update(User user);

    void updateAvatar(String picUrl);

    void updatePwd(String newPwd, Integer id);
}
