package com.github.fashion.test.controller;

import com.github.fashion.test.model.UserReq;
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
@RequestMapping("/list")
@Controller
public class ListController {


    @PostMapping("/test1")
    @ResponseBody
    @Validated
    public Object test1(@RequestBody List<UserReq> reqList){

        return reqList;
    }


    @PostMapping("/test2")
    @ResponseBody
    @Validated
    public Object test2(@RequestBody List<String> reqList){

        return reqList;
    }


}
