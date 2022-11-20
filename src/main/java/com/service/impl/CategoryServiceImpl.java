package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.CustomException;
import com.common.R;
import com.domain.Category;
import com.domain.Dish;
import com.domain.Setmeal;
import com.mapper.CategoryMapper;
import com.mapper.DishMapper;
import com.mapper.SetmealMapper;
import com.service.CategoryService;
import com.service.DishService;
import com.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {

        /*
        * 关联菜品的
        * */
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId, id);

        int dishCount = dishService.count(lambdaQueryWrapper);
        if (dishCount > 0){
            //抛出异常
            throw new CustomException("当前分类关联了菜品，不能删除");

        }

        /*
        * 关联套餐的
        * */
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId, id);

        int setMealCount = setmealService.count(lambdaQueryWrapper1);
        if (setMealCount > 0) {
            throw new CustomException("当前分类关联了菜品，不能删除");

        }

        super.removeById(id);
    }
}
