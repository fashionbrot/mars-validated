package com.github.fashion.test.controller;


import com.github.fashion.test.model.DefaultModel;
import com.github.fashionbrot.validated.annotation.Default;
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
    public String test(@Custom(min=1) String abc){
        return abc+"";
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

}
