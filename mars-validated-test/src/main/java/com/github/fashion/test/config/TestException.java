package com.github.fashion.test.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestException  extends RuntimeException {

    private int code;
    private String msg;



    public TestException(int code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }


}
