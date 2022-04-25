package com.github.fashionbrot.exception;

import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.exception.ValidatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(Exception e) {
        log.error("exception error:",e);
        return e.getMessage();
    }

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        List<MarsViolation> violations = e.getViolations();
        if (violations.size()==1){
            return violations.get(0).getMsg();
        }else {
            String msg = String.join(",", violations.stream().map(m -> m.getMsg()).collect(Collectors.toList()));
            return msg;
        }
    }


}
