package com.github.fashion.test.controller;

import com.alibaba.fastjson2.JSON;
import com.github.fashion.test.model.DefaultModel;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashion.test.test.Custom;
import com.github.fashion.test.test.CustomModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("custom")
public class CustomController {


    @RequestMapping("demo1")
    @ResponseBody
    @Validated(failFast = false)
    public String demo1(@Custom(min=1)String abc){

        return abc;
    }

    @RequestMapping("demo2")
    @ResponseBody
    @Validated
    public String demo2(@Custom(min=1) DefaultModel defaultModel){

        return JSON.toJSONString(defaultModel);
    }

    @RequestMapping("demo3")
    @ResponseBody
    @Validated
    public String demo3(@Custom(min=1) CustomModel customModel){

        return customModel.getAbc();
    }


}
