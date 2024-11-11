package com.itheima.mapper;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * CategoryMapper
 *
 * @author dingtao
 * @date 2024/8/1 16:21
 */
@Mapper
public interface CategoryMapper {

    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Select("select * from category where create_user = #{id}")
    List<Category> GetCategoryList(Integer id);

    @Select("select * from category where id=#{id}")
    Category CategoryDetail(Integer id);
}
