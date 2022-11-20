package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.R;
import com.domain.User;
import com.service.UserService;
import com.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();

        if(phone != null){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            log.info("code : {}", code);

            session.setAttribute(phone, code);

            return R.success("发送成功");
        }

        return R.error("发送失败");
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody Map map, HttpServletRequest request){

        String phone = map.get("phone").toString();

        String code = map.get("code").toString();

        String session = request.getSession().getAttribute(phone).toString();

        if(session.equals(code)){
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(lambdaQueryWrapper);

            if(user == null){
                user.setPhone(phone);
                userService.save(user);
            }

            request.getSession().setAttribute("user", user.getId());

            return R.success("验证成功");
        }

        return R.error("验证失败");
    }



}
