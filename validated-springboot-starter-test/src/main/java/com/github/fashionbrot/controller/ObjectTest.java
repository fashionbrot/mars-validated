package com.github.fashionbrot.controller;

import com.github.fashionbrot.constraint.ObjectBean;
import com.github.fashionbrot.entity.Test1Entity;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/object")
public class ObjectTest {

    @Validated
    @ResponseBody
    @GetMapping("/test1")
    public String test1(@ObjectBean(msg = "Test1Entity 验证失败了") Test1Entity req){

        return req.toString();
    }

    @Validated
    @ResponseBody
    @GetMapping("/test2")
    public String test2(Test1Entity req){

        return req.toString();
    }


    @Validated
    @ResponseBody
    @GetMapping("/test3")
    public String test3(@ObjectBean String  req){

        return req.toString();
    }

}
