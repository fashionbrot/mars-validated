package com.github.fashion.test.controller;

import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/l18n")
@Controller
public class I18nController {


    @Autowired
    private HttpServletRequest req;

    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test(@NotEmpty() String abc){
        return abc;
    }

}
