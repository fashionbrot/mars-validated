package com.github.fashion.test.controller;



import com.github.fashion.test.model.NotEmptyModel;
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
    @Validated(failFast = true)
    public NotEmptyModel  test(@NotEmpty(msg = "入参 abc is null") String abc){

        NotEmptyModel b=new NotEmptyModel();

        return b;
    }

    @RequestMapping("/notEmpty2")
    @ResponseBody
    @Validated(failFast = false)
    public String test(@NotEmpty NotEmptyModel notNullModel){
        return notNullModel.getAbc();
    }

}
