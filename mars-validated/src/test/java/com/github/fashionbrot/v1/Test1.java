package com.github.fashionbrot.v1;

import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.Validated;

public class Test1 {


    @Validated
    public void test1(@NotEmpty String abc){

    }

    @Validated
    public void test2(@NotEmpty String abc){

    }

}
