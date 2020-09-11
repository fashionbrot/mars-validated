package com.github.fashion.test.service;

import com.github.fashion.test.groups.AddGroup;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.test.Custom;
import org.springframework.stereotype.Service;

@Service
public class ValidService {


    @Validated(groups = AddGroup.class)
    public void test5(@Custom(min = 1,groups = AddGroup.class) String abc) {

        System.out.println("valid service");
    }
}