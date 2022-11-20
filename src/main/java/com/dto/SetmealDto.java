package com.dto;

import com.domain.Setmeal;
import com.domain.SetmealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    List<SetmealDish> list = new ArrayList<>();


    private String categoryName;

    private Integer copies;


}
