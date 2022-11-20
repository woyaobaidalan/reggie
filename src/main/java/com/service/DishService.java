package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.domain.Dish;
import com.dto.DishDto;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

}
