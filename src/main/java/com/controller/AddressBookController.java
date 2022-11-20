package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.common.R;
import com.domain.AddressBook;
import com.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /*
    * 新增地址
    * */
    @PostMapping
    public R<String> add(@RequestBody AddressBook addressBook, HttpServletRequest request){
        Long userID = (Long) request.getSession().getAttribute("user");
        addressBook.setUserId(userID);
        addressBookService.save(addressBook);

        return R.success("加入成功");
    }

    /*
    * 展示地址列表
    * */
    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        List<AddressBook> list = addressBookService.list();


        return R.success(list);
    }
    /*
    * 修改默认地址
    * */
    @PutMapping("/default")
    public R<String> updateDefault(@RequestBody AddressBook addressBook){

        Long id = addressBook.getId();

        log.info("进入到了这里，id ：{}", id);

        LambdaUpdateWrapper<AddressBook> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(AddressBook::getIsDefault, 0);

        addressBookService.update(lambdaUpdateWrapper);

        LambdaUpdateWrapper<AddressBook> lambdaUpdateWrapper1 = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper1.eq(AddressBook::getId, id);
        lambdaUpdateWrapper1.set(AddressBook::getIsDefault, 1);
        addressBookService.update(lambdaUpdateWrapper1);

        log.info("执行完了");

        return R.success("修改成功");
    }

    /*
    * 展示地址信息
    * */
    @GetMapping("/{id}")
    public R<AddressBook> show(@PathVariable Long id){

        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AddressBook::getId, id);
        AddressBook one = addressBookService.getOne(lambdaQueryWrapper);
        return R.success(one);
    }

    /*
    * 修改地址信息
    * */
    @PutMapping
    public R<String> updateInfo(@RequestBody AddressBook addressBook, HttpServletRequest request){

        Long userId = (Long) request.getSession().getAttribute("user");

        addressBook.setUserId(userId);
        addressBookService.updateById(addressBook);
        return R.success("修改成功");
    }

    /*
    * 删除地址
    * */
    @DeleteMapping
    public R<String> delete(Long ids){
        addressBookService.removeById(ids);


        return R.success("删除成功");
    }

    @GetMapping("/default")
    public R<AddressBook> show(){

        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook one = addressBookService.getOne(lambdaQueryWrapper);

        log.info("这个地址的信息为{}", one);
        return R.success(one);
    }



}
