package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * UserMapper
 *
 * @author dingtao
 * @date 2024/7/25 16:11
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{userName}")
    User findByUserName(String userName);

    @Insert("insert into user(username,password,create_time,update_time)" +
            "values(#{userName},#{password},now(),now())")
    void add(String userName, String password);

    @Update("update user set nickname = #{nickname},email = #{email}, update_time = #{updateTime} where id = #{id}")
    void update(User user);

    @Update("update user set user_pic = #{picUrl}, update_time = now() where id = #{id}")
    void updateAvatar(String picUrl, Integer id);

    @Update("update user set password = #{newPwd}, update_time = now() where id = #{id}")
    void updatePwd(String newPwd, Integer id);
}
