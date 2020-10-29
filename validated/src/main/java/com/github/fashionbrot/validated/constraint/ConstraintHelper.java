package com.github.fashionbrot.validated.constraint;



import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.internal.NotNullValid;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstraintHelper {


    private final Map<Class<? extends Annotation>,  Class<? extends ConstraintValidator >> builtinConstraints;

    public ConstraintHelper() {
        Map< Class<? extends Annotation>, Class<? extends ConstraintValidator >> temp=new HashMap<>();
        putTemp(temp, NotNull.class, NotNullValid.class);

        this.builtinConstraints = temp;
    }


    private static <A extends Annotation> void putTemp(
            Map<Class<? extends Annotation>, Class<? extends ConstraintValidator>> temp,
            Class<A> constraintType,
            Class<? extends ConstraintValidator<A,?> > validatorType) {
        temp.put(constraintType,validatorType);
    }


}
