package com.github.fashion.test.controller;

import com.github.fashionbrot.validated.annotation.Length;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("length")
public class LengthController {


    @RequestMapping("demo1")
    @ResponseBody
    @Validated
    public String demo1(@Length(min =1,max = 5,msg = "长度最长${max}最短${min } ")String test){

        return test;
    }


}
