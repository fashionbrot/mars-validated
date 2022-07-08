package com.github.fashion.test.service;

import com.github.fashion.test.groups.AddGroup;
import com.github.fashion.test.model.GroupModel;
import com.github.fashion.test.test.Custom;
import com.github.fashionbrot.validated.annotation.Validated;
import org.springframework.stereotype.Service;

@Service
public class ValidService {


    @Validated
    public void test5(@Custom(min = 1) String abcd, GroupModel model) {

        System.out.println("valid service");
    }

    public void test6( GroupModel model) {

        System.out.println("valid service");
    }
}
