package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.R;
import com.domain.Category;
import com.domain.Dish;
import com.domain.DishFlavor;
import com.dto.DishDto;
import com.service.CategoryService;
import com.service.DishFlavorService;
import com.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /*
    * 分页查询
    * */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        log.info("page : {}, pageSize: {}, name: {}", page, pageSize, name);

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName, name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, lambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<DishDto> list = new ArrayList<>();

        List<Dish> records = pageInfo.getRecords();
        for(Dish record : records){
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(record, dishDto);

            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);

            String CategoryName = category.getName();

            dishDto.setCategoryName(CategoryName);

            list.add(dishDto);
        }

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /*
    * 批量删除
    * */
    @DeleteMapping
    public R<String> delete(Long[] ids){

        for(Long id : ids){
            LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Dish::getId, id);
            dishService.removeById(id);
        }

        return R.success("批量删除成功！");
    }

    /*
    * 更改起售停售状态
    * */
    @PostMapping("/status/{id}")
    public R<String> update(@PathVariable Integer id, Long[] ids){

        for(Long ans: ids){
            LambdaUpdateWrapper<Dish> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Dish::getId, ans);
            lambdaUpdateWrapper.set(Dish::getStatus, id);

            dishService.update(lambdaUpdateWrapper);
        }

        return R.success("修改成功");
    }

    /*
    * 添加功能
    * */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        dishService.saveWithFlavor(dishDto);

        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        //dishDto.setCategoryName();

        return R.success(dishDto);
    }

    /*
    * 获取菜品信息
    * */
    @GetMapping("/list")
    public R<List<DishDto>> listDish(Dish dish){

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lambdaQueryWrapper.eq(Dish::getStatus, 1);

        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime).orderByAsc(Dish::getSort);

        List<Dish> list = dishService.list(lambdaQueryWrapper);

        List<DishDto> dishDtoList = new ArrayList<>();

        for(Dish oneDish : list){
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(oneDish, dishDto);
            Long id = oneDish.getId();

            LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
            lqw.eq(DishFlavor :: getDishId, id);
            List<DishFlavor> list1 = dishFlavorService.list(lqw);

            dishDto.setFlavors(list1);
            dishDtoList.add(dishDto);

        }


        return R.success(dishDtoList);
    }









}
