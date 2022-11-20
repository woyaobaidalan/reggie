package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.BaseContext;
import com.common.R;
import com.domain.*;
import com.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrdersDetailService ordersDetailService;

    /*
    * 订单分页
    * */
    @GetMapping("/userPage")
    public R<Page> show(Integer page, Integer pageSize){

        Page<Orders> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Orders::getOrderTime);

        ordersService.page(pageInfo, lambdaQueryWrapper);


        return R.success(pageInfo);
    }

    /*
    * 手机提交订单
    * */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);

        orders.setAddress(addressBook.getDetail());
        orders.setAddressBookId(addressBook.getId());
        orders.setConsignee(addressBook.getConsignee());

        Long userId = BaseContext.getCurrentId();
        orders.setUserId(userId);

        User user = userService.getById(userId);
        orders.setUserName(user.getName());
        orders.setPhone(user.getPhone());


        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());

        log.info("这个订单的具体情况：{}", orders);


        List<ShoppingCart> list = shoppingCartService.list();
        double amount = 0;
        for(ShoppingCart shoppingCart : list){
            amount += shoppingCart.getAmount();
        }

        orders.setAmount(amount);

        ordersService.save(orders);
//        List<ShoppingCart> listShoppingCart = shoppingCartService.list();

//        List<OrderDetail> orderDetailList = new ArrayList<>();
//        for(ShoppingCart shoppingCart : listShoppingCart){
//
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setName(shoppingCart.getName());
//            orderDetail.setImage(shoppingCart.getImage());
//            orderDetail.setOrderId(orders.getId());
//            orderDetail.setDishId(shoppingCart.getDishId());
//            orderDetail.setSetmealId(shoppingCart.getSetmealId());
//            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
//            orderDetail.setNumber(shoppingCart.getNumber());
//            orderDetail.setAmount(shoppingCart.getAmount());
//
//            orderDetailList.add(orderDetail);
//        }
//
//        ordersDetailService.saveBatch(orderDetailList);

        return R.success("提交订单成功");
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, Long id){
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, id);

        Page<Orders> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!StringUtils.isNotEmpty(String.valueOf(id)), Orders::getId, id);
        lambdaQueryWrapper.orderByDesc(Orders::getOrderTime);


        ordersService.page(pageInfo, lambdaQueryWrapper);


        return R.success(pageInfo);
    }

}
