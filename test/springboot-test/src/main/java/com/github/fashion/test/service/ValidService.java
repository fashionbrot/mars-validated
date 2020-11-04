package com.github.fashion.test.service;

import com.github.fashion.test.groups.AddGroup;
import com.github.fashion.test.model.GroupModel;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.test.Custom;
import org.springframework.stereotype.Service;

@Service
public class ValidService {


//    @Validated(groups = AddGroup.class)
    public void test5(@Custom(min = 1) String abc, GroupModel model) {

        System.out.println("valid service");
    }

    public void test6( GroupModel model) {

        System.out.println("valid service");
    }
}
