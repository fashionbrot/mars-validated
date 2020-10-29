package com.github.fashion.test.controller;


import com.github.fashion.test.util.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Controller
@RequestMapping("/compare")
@Validated
public class HibernateCompareMars {


    @RequestMapping("/demo1")
    @ResponseBody
    public String demo1(@Valid @Validated @NotNull(message = "验证码长度应该在4-6位之间") String abc){

        return abc;
    }


    @RequestMapping("/demo2")
    @ResponseBody
    @com.github.fashionbrot.validated.annotation.Validated
    public String demo2(@com.github.fashionbrot.validated.annotation.NotNull( msg = "验证码长度应该在4-6位之间") String abc){
        return abc;
    }

    @RequestMapping("/demo3")
    @ResponseBody
    public String demo3(Integer count){
        long start = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            HttpClientUtil.httpGet("http://localhost:8080/compare/demo1?i"+i,null,null,false);
        }
        long end = System.currentTimeMillis();


        long start2 = System.currentTimeMillis();
        /*for(int j=0;j<count;j++){
            HttpClientUtil.httpGet("http://localhost:8080/compare/demo1?"+j,null,null,false);
        }*/
        long start3 = System.currentTimeMillis();
        System.out.println(" 一共耗时："+(end-start));
        System.out.println("mars      一共耗时："+(start3-start2));

        return "ok"+(end-start);
    }

    @RequestMapping("/demo4")
    @ResponseBody
    public String demo4(Integer count){
        long start = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            HttpClientUtil.httpGet("http://localhost:8080/compare/demo2?i"+i,null,null,false);
        }
        long end = System.currentTimeMillis();


        long start2 = System.currentTimeMillis();
        /*for(int j=0;j<count;j++){
            HttpClientUtil.httpGet("http://localhost:8080/compare/demo1?"+j,null,null,false);
        }*/
        long start3 = System.currentTimeMillis();
        System.out.println(" 一共耗时："+(end-start));
        System.out.println("mars      一共耗时："+(start3-start2));

        return "ok"+(end-start);
    }

}
