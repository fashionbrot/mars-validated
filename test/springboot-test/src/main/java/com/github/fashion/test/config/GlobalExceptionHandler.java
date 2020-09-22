package com.github.fashion.test.config;

import com.github.fashionbrot.validated.exception.ValidatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(TestException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object marsException(TestException e) {
        return e.getMsg();
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(Exception e) {
        log.error("exception error:",e);
        return e.getMessage();
    }


    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        log.error("exception error:",e);
        return e.getMessage();
    }


}
