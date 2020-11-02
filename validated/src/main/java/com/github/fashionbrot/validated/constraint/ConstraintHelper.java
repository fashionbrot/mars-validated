package com.github.fashionbrot.validated.constraint;


import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.internal.ModifyValid;
import com.github.fashionbrot.validated.internal.NotNullValid;
import com.github.fashionbrot.validated.util.MethodUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConstraintHelper {


    private static Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraint = new ConcurrentHashMap<>();


    static {
        Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraints = new HashMap<>();
        putTemp(builtinConstraints, NotNull.class, NotNullValid.class);
        putTemp(builtinConstraints, Default.class, ModifyValid.class);
        builtinConstraint.putAll(builtinConstraints);
    }


    public static <A extends Annotation> void putTemp(
            Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraints,
            Class<A> constraintType,
            Class<? extends ConstraintValidator>... constraintValidators) {
        if (constraintValidators.length > 0) {
            List<ConstraintValidator> list = new ArrayList<>(constraintValidators.length);
            for (int i = 0; i < constraintValidators.length; i++) {
                list.add(MethodUtil.newInstance(constraintValidators[i]));
            }
            builtinConstraints.put(constraintType, list);
        }
    }

    public static <A extends Annotation> void putConstraintValidator(
            Class<A> constraintType,
            Class<? extends ConstraintValidator>[] constraintValidators) {
        if (constraintValidators.length > 1) {
            List<ConstraintValidator> list = new ArrayList<>(constraintValidators.length);
            for (int i = 0; i < constraintValidators.length; i++) {
                list.add(MethodUtil.newInstance(constraintValidators[i]));
            }
            builtinConstraint.putIfAbsent(constraintType, list);
        }
    }


    public static <A extends Annotation> List<ConstraintValidator> getConstraint(Class<A> constraintType) {
        if (builtinConstraint.containsKey(constraintType)) {
            return builtinConstraint.get(constraintType);
        }
        return null;
    }


}
