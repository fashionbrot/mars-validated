package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.exception.ValidatedException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class ExceptionUtil {

    private static final ThreadLocal<List<MarsViolation>> LOCAL = ThreadLocal.withInitial(ArrayList::new);


    public static void addMarsViolation(MarsViolation marsViolation){
        LOCAL.get().add(marsViolation);
    }

    public static List<MarsViolation> getMarsViolationList(){
        return LOCAL.get();
    }

    public static void reset(){
        LOCAL.remove();
    }


    public static void throwException(){
        List<MarsViolation> marsViolationList = getMarsViolationList();
        if (ObjectUtil.isNotEmpty(marsViolationList)) {
            throw new ValidatedException(marsViolationList);
        }
    }



}
