package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.R;
import com.domain.Category;
import com.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody Category category){

        categoryService.save(category);

        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){

        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, lambdaQueryWrapper);

        return R.success(pageInfo);

    }

    @PutMapping
    public R<String> update(@RequestBody Category category){

        log.info("category{}", category.getId());

        categoryService.updateById(category);


        return R.success("修改成功");
    }


    @DeleteMapping
    public R<String> delete(Long ids){

        log.info("删除菜品的id是{}", ids);

        categoryService.remove(ids);

        return R.success("删除成功");
    }

    /*
     * 展示菜品分类的列表
     * */
    @GetMapping("/list")
    public R<List<Category>> list(){
        List<Category> list = categoryService.list();
        log.info("所有的菜品的值: {}", list);

        return R.success(list);
    }



}
