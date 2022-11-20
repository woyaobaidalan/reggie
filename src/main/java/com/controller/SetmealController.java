package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.R;
import com.domain.Category;
import com.domain.Dish;
import com.domain.Setmeal;
import com.domain.SetmealDish;
import com.dto.SetmealDto;
import com.service.CategoryService;
import com.service.DishService;
import com.service.SetmealDishService;
import com.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    /*
    *分页查询...
    * */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

//        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
//        Page<SetmealDto> setmealDtoPage = new Page<>();
//
//        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Setmeal ::getName, name);
//        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
//
//        setmealService.page(pageInfo, lambdaQueryWrapper);
//
//        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
//
//        List<SetmealDto> list = new ArrayList<>();
//
//        List<Setmeal> records = pageInfo.getRecords();
//        for(Setmeal record : records){
//            SetmealDto setmealDto = new SetmealDto();
//            BeanUtils.copyProperties(record, setmealDto);
//
//            Long categoryId = record.getCategoryId();
//            Category category = categoryService.getById(categoryId);
//            String categoryName = category.getName();
//
//            setmealDto.setCategoryName(categoryName);
//            list.add(setmealDto);
//        }
//
//        setmealDtoPage.setRecords(list);
//
//        return R.success(setmealDtoPage);

        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, lambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, setmealDtoPage);

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = new ArrayList<>();

        for(Setmeal record : records){
            SetmealDto setmealDto = new SetmealDto();
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();

            BeanUtils.copyProperties(record, setmealDto);

            setmealDto.setCategoryName(categoryName);

            list.add(setmealDto);
        }

        setmealDtoPage.setRecords(list);


        return R.success(setmealDtoPage);
    }

    @DeleteMapping()
    public R<String> delete(Long[] ids){

        for(Long id : ids){
            setmealService.removeById(id);
        }

        return R.success("删除成功");
    }

    @PostMapping("/status/{id}")
    public R<String> update(@PathVariable Integer id, Long[] ids){

        LambdaUpdateWrapper<Setmeal> lambdaQueryWrapper = new LambdaUpdateWrapper<>();
        for(Long setmealID : ids ){
            lambdaQueryWrapper.eq(Setmeal :: getId, setmealID);
            lambdaQueryWrapper.set(Setmeal:: getStatus, id);

            setmealService.update(lambdaQueryWrapper);
        }

        return R.success("修改成功");
    }

    /*
    * 新建套餐
    * */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        setmealService.SaveSetMealAndDish(setmealDto);

        return R.success("添加成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){

        log.info("setmeal的值是：{}", setmeal.getCategoryId());

        Long categoryId = setmeal.getCategoryId();

        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Setmeal::getCategoryId, categoryId);

        List<Setmeal> setMealList = setmealService.list(lambdaQueryWrapper);


        return R.success(setMealList);
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> showDish(@PathVariable Long id){

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);


        return R.success(list);
    }


    @GetMapping("/{id}")
    public R<SetmealDto> update(@PathVariable Long id){

        Setmeal setmeal = setmealService.getById(id);

        SetmealDto setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);

        setmealDto.setList(list);

        return R.success(setmealDto);
    }


}
