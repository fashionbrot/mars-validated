package com.github.fashion.test.controller;

import com.alibaba.fastjson.JSON;
import com.github.fashion.test.model.DefaultModel;
import com.github.fashion.test.model.SizeModel;
import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.Size;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class SizeController {


    @RequestMapping("sizeString")
    @ResponseBody
    @Validated
    public String sizeString(@Size(min = 1,max = 3,msg = "size 在 1~3之间") String abc){

        return abc;
    }
    @RequestMapping("sizeString2")
    @ResponseBody
    @Validated
    public String sizeString(SizeModel sizeModel){

        return sizeModel.getAbc();
    }

    @RequestMapping("sizeMap")
    @ResponseBody
    @Validated
    public String sizeMap(@RequestBody @Size(min = 1,max = 3,msg = "size 在 1~3之间")Map map){

        return map.toString();
    }

}
