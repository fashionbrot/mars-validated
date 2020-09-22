package com.github.fashionbrot.validated.exception;

import lombok.Data;

@Data
public class ValidatedException extends RuntimeException  {

    private String fieldName;

    private String msg;

    private Object value;

    public ValidatedException(String msg,String fieldName) {
        super(msg);
        this.msg=msg;
        this.fieldName=fieldName;
    }

    public ValidatedException(String msg,Object value) {
        super(msg);
        this.msg=msg;
        this.value = value;
    }

}
