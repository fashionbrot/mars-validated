package com.github.fashion.test.controller;


import com.github.fashion.test.util.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Controller
@RequestMapping("/compare")
@Validated
public class HibernateCompareMars {


    @RequestMapping("/demo1")
    @ResponseBody
    public String demo1(@Valid @Validated @DecimalMax(message = "验证码长度应该在4-6位之间", value = "0") String abc){

        return abc;
    }


    @RequestMapping("/demo2")
    @ResponseBody
    @com.github.fashionbrot.validated.annotation.Validated
    public String demo2(@com.github.fashionbrot.validated.annotation.Size(min = 6, max = 10, msg = "验证码长度应该在4-6位之间") String abc){
        return abc;
    }

    @RequestMapping("/demo3")
    @ResponseBody
    public String demo3(){
        long start = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            HttpClientUtil.httpGet("http://localhost:8080/compare/demo2",null,null,false);
        }
        long end = System.currentTimeMillis();
        System.out.println(" 一共耗时："+(end-start));

       /* long start2 = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            HttpClientUtil.httpGet("http://localhost:8080/compare/demo1",null,null,false);
        }
        System.out.println("hibernate 一共耗时："+(end-start));
        System.out.println("mars      一共耗时："+(System.currentTimeMillis()-start2));*/

        return "ok";
    }

}
