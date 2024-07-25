package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
