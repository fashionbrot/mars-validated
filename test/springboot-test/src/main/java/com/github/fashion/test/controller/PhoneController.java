package com.github.fashion.test.controller;

import com.github.fashion.test.model.BaseModel;
import com.github.fashionbrot.validated.annotation.Phone;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/phone")
@Controller
public class PhoneController {


    @RequestMapping("/test1")
    @ResponseBody
    @Validated(validReturnValue = true)
    public BaseModel test1(@Phone()String phone){

        return new BaseModel();
    }



}
