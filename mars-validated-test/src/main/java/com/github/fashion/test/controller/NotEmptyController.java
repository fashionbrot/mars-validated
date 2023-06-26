package com.github.fashion.test.controller;



import com.github.fashion.test.model.NotEmptyModel;
import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.groups.DefaultGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotEmptyController {

    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated(failFast = true,groups = {DefaultGroup.class})
    public NotEmptyModel  test(@NotEmpty(groups = {NotEmptyController.class}) String abc){

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
