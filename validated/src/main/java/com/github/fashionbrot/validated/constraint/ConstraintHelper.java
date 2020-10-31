package com.github.fashionbrot.validated.constraint;



import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.internal.NotNullValid;
import com.github.fashionbrot.validated.util.MethodUtil;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstraintHelper {


    private static final Map<Class<? extends Annotation>,  ConstraintValidator> builtinConstraints;

    static {
        Map< Class<? extends Annotation>, ConstraintValidator> temp=new HashMap<>();
        putTemp(temp, NotNull.class, MethodUtil.newInstance(NotNullValid.class));
        builtinConstraints = temp;
    }




    private static <A extends Annotation> void putTemp(
            Map<Class<? extends Annotation>, ConstraintValidator> temp,
            Class<A> constraintType,
            ConstraintValidator validatorType) {
        temp.put(constraintType,validatorType);
    }


    public static <A extends Annotation> ConstraintValidator getConstraint(Class<A> constraintType){
        if (builtinConstraints.containsKey(constraintType)){
            return  builtinConstraints.get(constraintType);
        }
        return null;
    }



}
