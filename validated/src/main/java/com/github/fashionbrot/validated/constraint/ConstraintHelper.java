package com.github.fashionbrot.validated.constraint;



import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.internal.NotNullValid;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstraintHelper {


    private static final Map<Class<? extends Annotation>,  Class<? extends ConstraintValidator >> builtinConstraints;

    static {
        Map< Class<? extends Annotation>, Class<? extends ConstraintValidator >> temp=new HashMap<>();
        putTemp(temp, NotNull.class, NotNullValid.class);
        builtinConstraints = temp;
    }




    private static <A extends Annotation> void putTemp(
            Map<Class<? extends Annotation>, Class<? extends ConstraintValidator>> temp,
            Class<A> constraintType,
            Class<? extends ConstraintValidator<A,?> > validatorType) {
        temp.put(constraintType,validatorType);
    }


    public static <A extends Annotation> Class<? extends ConstraintValidator<A,?> > getConstraint(Class<A> constraintType){
        if (builtinConstraints.containsKey(constraintType)){
            return (Class<? extends ConstraintValidator<A, ?>>) builtinConstraints.get(constraintType);
        }
        return null;
    }

}
