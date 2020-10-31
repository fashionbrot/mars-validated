package com.github.fashion.test.controller;



import com.github.fashion.test.model.NotNullModel;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotNullController {



    @RequestMapping("/notnull")
    @ResponseBody
    @Validated
    public String test(@NotNull(msg = "null了") String abc){
        return abc;
    }

    @RequestMapping("/notnullBean")
    @ResponseBody
    @Validated
    public String test(NotNullModel notNullModel){
        return notNullModel.getAbc();
    }

}
