package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.*;
import com.github.fashionbrot.validated.util.PatternSts;

public class DemoModel {

    @NotEmpty
    @javax.validation.constraints.NotEmpty
    private String test1;

    @NotNull
    @javax.validation.constraints.NotNull
    private String test2;
/*
    @Size(msg = "test3 is null",min = 10,max = 12)
    @javax.validation.constraints.Size(message = "test3 is null",min = 10,max = 12)
    private String test3;

    @Pattern(msg = "test4 is null",regexp = PatternSts.EMAIL_REGEXP)
    @javax.validation.constraints.Pattern(message = "test4 is null",regexp = PatternSts.EMAIL_REGEXP)
    private String test4;

    @Email(msg = "test5 is null")
    @javax.validation.constraints.Email(message = "test5 is null")
    private String test5;

    @NotEmpty(msg = "test6 is null")
    @javax.validation.constraints.NotEmpty(message = "test6 is null")
    private String test6;

    @NotEmpty(msg = "test7 is null")
    @javax.validation.constraints.NotEmpty(message = "test7 is null")
    private String test7;

    @NotEmpty(msg = "test8 is null")
    @javax.validation.constraints.NotEmpty(message = "test8 is null")
    private String test8;

    @NotEmpty(msg = "test9 is null")
    @javax.validation.constraints.NotEmpty(message = "test9 is null")
    private String test9;


    @NotEmpty(msg = "test10 is null")
    @javax.validation.constraints.NotEmpty(message = "test10 is null")
    private String test10;*/
}
