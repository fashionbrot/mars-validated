package com.github.fashion.test.controller;

import com.github.fashionbrot.validated.annotation.NotEqualLength;
import com.github.fashionbrot.validated.annotation.Size;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class NotEqualsLengthController {


    @RequestMapping("notEqualsLength")
    @ResponseBody
    @Validated
    public String sizeString(@NotEqualLength(length = 2,msg = "不在 2个长度之间") String abc){

        return abc;
    }

}
