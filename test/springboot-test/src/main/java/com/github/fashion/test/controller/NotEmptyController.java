package com.github.fashion.test.controller;



import com.github.fashion.test.model.NotEmptyModel;
import com.github.fashion.test.model.NotNullModel;
import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotEmptyController {



    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated(failFast = false)
    public String test(@NotEmpty(msg = "abc is null") String abc){
        return abc;
    }

    @RequestMapping("/notEmpty2")
    @ResponseBody
    @Validated(failFast = false)
    public String test(@NotEmpty NotEmptyModel notNullModel){
        return notNullModel.getAbc();
    }

}
