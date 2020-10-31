package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.exception.ValidatedException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class ExceptionUtil {


    public static void throwException(List<MarsViolation> violationSet){
        throw new ValidatedException(violationSet);
    }


}
