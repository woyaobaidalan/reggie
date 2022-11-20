package com.utils;

import com.common.R;

import java.util.Random;

public class ValidateCodeUtils {

    /*
    * 生成验证码
    * */
    public static Integer generateValidateCode(int length){
        Integer code = null;
        /*
        * 判断是几位数
        * */
        if(length == 4){
            code = new Random().nextInt(9999);
            if(code < 1000){
                code += 1000;
            }
        }else if(length == 6){
            code = new Random().nextInt(99999);
            if(code < 10000){
                code += 100000;
            }
        }else{
            throw new RuntimeException("只能生成4位或6位验证码");
        }

        return code;

    }


}
