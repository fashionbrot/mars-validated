package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.NotEmpty;

public class DemoModel {

    @NotEmpty(msg = "test1 is null")
    @javax.validation.constraints.NotEmpty(message = "test1 is null")
    private String test1;

    @NotEmpty(msg = "test2 is null")
    @javax.validation.constraints.NotEmpty(message = "test2 is null")
    private String test2;

    @NotEmpty(msg = "test3 is null")
    @javax.validation.constraints.NotEmpty(message = "test3 is null")
    private String test3;

    @NotEmpty(msg = "test4 is null")
    @javax.validation.constraints.NotEmpty(message = "test4 is null")
    private String test4;

    @NotEmpty(msg = "test5 is null")
    @javax.validation.constraints.NotEmpty(message = "test5 is null")
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
    private String test10;
}
