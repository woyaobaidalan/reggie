package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Setmeal;
import com.domain.SetmealDish;
import com.dto.SetmealDto;
import com.mapper.SetmealMapper;
import com.service.SetmealDishService;
import com.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public void SaveSetMealAndDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        Long setmealId = setmealDto.getId();
        List<SetmealDish> list = setmealDto.getList();

        for(SetmealDish setmealDish : list){

            Long dishId = setmealDish.getDishId();

            setmealDish.setSetmealId(setmealId);
            setmealDish.setDishId(dishId);
        }

        setmealDishService.saveBatch(list);


    }

    @Override
    public List<SetmealDto> ShowSetMealDtoList(SetmealDto setmealDto) {



        return null;
    }
}
