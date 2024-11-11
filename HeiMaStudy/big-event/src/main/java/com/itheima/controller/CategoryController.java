package com.itheima.controller;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itheima.pojo.Result.success;

/**
 * CategoryController
 * 
 * @author dingtao
 * @date 2024/8/1 16:17
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //添加文章分类
    @PostMapping
    public Result add(@RequestBody @Validated Category category){
        categoryService.addCategory(category);
        return success();
    }

    @GetMapping
    public Result<List<Category>> CategoryList(){
        categoryService.GetCategoryList();
        return success(categoryService.GetCategoryList());
    }

    @GetMapping
    public Result<Category> CategoryDetail(Integer id){
        Category category = categoryService.CategoryDetail(id);
        return Result.success(category);
    }

}
