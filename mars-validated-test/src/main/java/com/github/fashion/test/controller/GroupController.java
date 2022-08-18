package com.github.fashion.test.controller;

import com.github.fashion.test.annotation.CustomBean;
import com.github.fashion.test.groups.EditGroup;
import com.github.fashion.test.model.GroupModel;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.groups.DefaultGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/group")
public class GroupController {

    @GetMapping("add")
    @ResponseBody
    @Validated(failFast = false)
    public String add(GroupModel groupModel){
        return "ok";
    }

    @GetMapping("edit")
    @ResponseBody
    @Validated(groups ={EditGroup.class,DefaultGroup.class},failFast = false)
    public String edit(GroupModel groupModel){
        return "ok";
    }

    @GetMapping("edit2")
    @ResponseBody
    @Validated(groups ={EditGroup.class,DefaultGroup.class},failFast = false)
    public String edit2(@NotNull String abc,@NotNull String aaa){
        return "ok";
    }

    @PostMapping("postEdit")
    @ResponseBody
    @Validated(groups ={EditGroup.class,DefaultGroup.class},failFast = false)
    public String postEdit(@RequestBody GroupModel groupModel){
        return "ok";
    }

    @PostMapping("postEdit2")
    @ResponseBody
    @Validated(groups ={EditGroup.class,DefaultGroup.class},failFast = false)
    public String postEdit2(@CustomBean @RequestBody GroupModel groupModel){
        return "ok";
    }

}
