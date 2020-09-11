package com.github.fashion.test.controller;


import com.github.fashion.test.customAnnotation.CustomAnnotationTest;
import com.github.fashion.test.groups.AddGroup;
import com.github.fashion.test.model.DefaultModel;
import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.NotBlank;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.test.Custom;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {


    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test( String abc,@Custom(min = 1) String abc1){
        return abc+"："+abc1;
    }

    @RequestMapping("/test1")
    @ResponseBody
    @Validated
    public String test1( @Custom(min = 1,groups = {AddGroup.class}) String abc1){
        return abc1;
    }


    @RequestMapping("/test2")
    @ResponseBody
    @Validated
    public String test2(DefaultModel defaultModel){
        return defaultModel.getAbc()+":"+defaultModel.getWang();
    }


    @RequestMapping("/test3")
    @ResponseBody
    @Validated
    public String test3(DefaultModel defaultModel){
        return defaultModel.getPageNo()+":"+defaultModel.getPageSize();
    }


    @RequestMapping("/test4")
    @ResponseBody
    @Validated
    public String test(@CustomAnnotationTest String abc){
        return abc;
    }
}
