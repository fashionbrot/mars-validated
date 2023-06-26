package com.github.fashion.test.test;

import com.github.fashionbrot.validated.constraint.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomConstraintValidator.class,CustomConstraintValidator.class})
public @interface Custom {

    String msg() default "validated.Custom.msg";

    int min();

    //Class<?>[] groups() default  {};
}
