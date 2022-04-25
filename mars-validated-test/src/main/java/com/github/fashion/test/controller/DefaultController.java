package com.github.fashion.test.controller;

import com.alibaba.fastjson.JSON;
import com.github.fashion.test.model.DefaultModel;
import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("default")
public class DefaultController {


    @RequestMapping("demo1")
    @ResponseBody
    @Validated
    public String demo1(@Default(value = "12")Short abc,@Default(value = "demo1")String test){

        return abc+":"+test;
    }

    @RequestMapping("demo2")
    @ResponseBody
    @Validated
    public String demo2(DefaultModel defaultModel){

        return JSON.toJSONString(defaultModel);
    }

}
