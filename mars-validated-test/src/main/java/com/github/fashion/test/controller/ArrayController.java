package com.github.fashion.test.controller;

import com.github.fashion.test.model.UserReq;
import com.github.fashionbrot.validated.annotation.Valid;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author fashionbrot
 */
@RequestMapping("/array")
@Controller
@org.springframework.validation.annotation.Validated
public class ArrayController {


    @PostMapping("/test1")
    @ResponseBody
    @Validated
    public Object test1(@RequestBody @Valid UserReq[] reqList){

        return reqList;
    }

    @PostMapping("/test2")
    @ResponseBody
    @Validated
    public Object test2(@RequestBody @Valid List<UserReq> reqList){

        return reqList;
    }



    @PostMapping("/test3")
    @ResponseBody
    @Validated
    public Object test3(@RequestBody  String[] reqList){

        return reqList;
    }


}
