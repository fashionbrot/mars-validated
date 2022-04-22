package com.github.fashion.test.controller;

import com.github.fashion.test.annotation.CustomBean;
import com.github.fashion.test.model.ValidBeanModel;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/valid/bean")
@Controller
public class ValidBeanController {


    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test(@CustomBean ValidBeanModel validBeanModel){
        return validBeanModel.getA1()+validBeanModel.getA2();
    }

}
