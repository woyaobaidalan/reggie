package com.controller;

import com.common.BaseContext;
import com.common.R;
import com.domain.ShoppingCart;
import com.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<String> list(@RequestBody ShoppingCart shoppingCart){

        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        shoppingCart.setCreateTime(LocalDateTime.now());

        shoppingCartService.save(shoppingCart);

        return R.success("添加成功");
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){

        List<ShoppingCart> list = shoppingCartService.list();


        return R.success(list);
    }

}
