package com.github.fashion.test.model;

import com.github.fashion.test.groups.AddGroup;
import com.github.fashion.test.groups.EditGroup;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.test.Custom;

public class GroupModel {


    @Custom(min = 3,groups = {EditGroup.class,AddGroup.class})
    private String abc;


    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
}
