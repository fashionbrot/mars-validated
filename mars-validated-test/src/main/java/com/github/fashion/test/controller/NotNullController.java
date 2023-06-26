package com.github.fashion.test.controller;



import com.github.fashion.test.model.NotNullModel;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.annotation.Validated;

import com.github.fashionbrot.validated.groups.DefaultGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotNullController {



    @RequestMapping("/notnull")
    @ResponseBody
    @Validated(failFast = false)
    public String test(@NotNull(msg = "abc is null") String abc){
        return abc;
    }

    @RequestMapping("/notnullBean")
    @ResponseBody
    @Validated(failFast = false,groups = {DefaultGroup.class})
    public String test(NotNullModel notNullModel){
        return "ok";
    }

}
