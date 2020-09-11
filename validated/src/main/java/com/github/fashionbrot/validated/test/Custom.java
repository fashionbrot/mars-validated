package com.github.fashionbrot.validated.test;

import com.github.fashionbrot.validated.constraint.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomConstraintValidator.class,CustomConstraintValidator2.class})
public @interface Custom {

    String msg() default "com.sgr.valid.Custom.msg";

    int min();

    Class<?>[] groups() default  {};
}
