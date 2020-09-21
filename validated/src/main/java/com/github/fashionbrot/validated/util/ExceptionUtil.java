package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.exception.ValidatedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionUtil {

    public static void throwException(Object message,String fieldName){
        if (message!=null) {
            String msg = ValidatorUtil.filterMsg(message.toString());
            if (StringUtil.isNotBlank(msg)) {
                throw new ValidatedException(msg, fieldName);
            }
        }
    }

    public static void throwException(Object message){
        if (message!=null) {
            String msg = ValidatorUtil.filterMsg(message.toString());
            if (StringUtil.isNotBlank(msg)) {
                throw new ValidatedException(msg);
            }
        }
    }

    public static void throwException(String message,String fieldName){
        if (StringUtil.isNotBlank(message)) {
            String msg = ValidatorUtil.filterMsg(message);
            if (StringUtil.isNotBlank(msg)) {
                if (log.isDebugEnabled()) {
                    log.debug("ExceptionUtil msg:" + message +" fieldName:" + fieldName);
                }
                throw new ValidatedException(msg, fieldName);
            }
        }
    }

    public static void throwExceptionNotCheckMsg(String message,String fieldName){
        if (StringUtil.isNotBlank(message)) {
            if (log.isDebugEnabled()) {
                log.debug("ExceptionUtil msg:" + message +" fieldName:" + fieldName);
            }
            throw new ValidatedException(message, fieldName);
        }
    }

}
