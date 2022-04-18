package com.github.fashionbrot.controller;


import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotEmptyController {



    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated(failFast = true)
    public String  test(@NotEmpty(msg = "入参 abc is null") String abc){

        return "ok";
    }


}
