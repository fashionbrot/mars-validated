package com.github.fashion.test.controller;


import com.github.fashion.test.customAnnotation.CustomAnnotationTest;
import com.github.fashion.test.groups.AddGroup;
import com.github.fashion.test.groups.EditGroup;
import com.github.fashion.test.model.DefaultModel;
import com.github.fashion.test.model.GroupModel;
import com.github.fashion.test.service.ValidService;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashion.test.test.Custom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private ValidService validService;

    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test( String abc,@Custom(min = 1) String abc1){
        return abc+"："+abc1;
    }

    @RequestMapping("/test1")
    @ResponseBody
    @Validated(groups = {EditGroup.class})
    public String test1( @Custom(min = 1) String abc1){
        return abc1;
    }


    @RequestMapping("/test2")
    @ResponseBody
    @Validated(groups = AddGroup.class)
    public String test2(GroupModel groupModel){
        return groupModel.getAbc();
    }


    @RequestMapping("/test3")
    @ResponseBody
    @Validated
    public String test3(DefaultModel defaultModel){
        return defaultModel.getPageNo()+":"+defaultModel.getPageSize();
    }


    @RequestMapping("/test4")
    @ResponseBody
    @Validated
    public String test(@CustomAnnotationTest String abc){
        return abc;
    }


    @RequestMapping("/test5")
    @ResponseBody
    public String test5(String abc){
        validService.test5(abc,new GroupModel());
        return "test5";
    }

    @RequestMapping("/test6")
    @ResponseBody
    public String test6(GroupModel abc){
        validService.test6(abc);
        return "test6";
    }
}
