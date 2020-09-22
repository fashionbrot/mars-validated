package com.github.fashion.test.controller;

import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.test.Custom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

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
