package com.itheima.service;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;

import java.util.List;

/**
 * CategoryService
 *
 * @author dingtao
 * @date 2024/8/1 16:18
 */
public interface CategoryService {
    void addCategory(Category category);

    List<Category> GetCategoryList();

    Category CategoryDetail(Integer id);
}
