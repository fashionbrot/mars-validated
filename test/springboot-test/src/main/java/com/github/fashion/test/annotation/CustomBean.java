package com.github.fashion.test.annotation;

import com.github.fashionbrot.validated.constraint.Constraint;
import lombok.Data;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedByBean = {CustomBeanConstraintValidatorBean.class})
public @interface CustomBean {

    String msg() default "com.sgr.valid.Custom.msg";

    Class<?>[] groups() default  {};

}
