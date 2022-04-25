package com.github.fashion.test.model;

import com.github.fashion.test.test.Custom;

public class GroupModel {


    @Custom(min = 3)
    private String abc;


    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
}
