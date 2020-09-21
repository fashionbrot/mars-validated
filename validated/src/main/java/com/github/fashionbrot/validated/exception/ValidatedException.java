package com.github.fashionbrot.validated.exception;

import lombok.Data;

@Data
public class ValidatedException extends RuntimeException  {

    private String fieldName;

    private String msg;

    public ValidatedException(String msg,String fieldName) {
        super(msg);
        this.msg=msg;
        this.fieldName=fieldName;
    }

    public ValidatedException(String msg) {
        super(msg);
        this.msg=msg;
    }

}
