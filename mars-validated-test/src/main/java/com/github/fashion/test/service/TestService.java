package com.github.fashion.test.service;


import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Validated
    public void test1(@NotEmpty(msg = "测试service直接使用 notEmpty不能为空") String abc){

        System.out.println("test1");
    }

}
