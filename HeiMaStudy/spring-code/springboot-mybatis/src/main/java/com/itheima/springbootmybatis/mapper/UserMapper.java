package com.itheima.springbootmybatis.mapper;

import com.itheima.springbootmybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * UserMapper
 *
 * @author dingtao
 * @date 2024/7/24 14:52
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findById(Integer id);
}
