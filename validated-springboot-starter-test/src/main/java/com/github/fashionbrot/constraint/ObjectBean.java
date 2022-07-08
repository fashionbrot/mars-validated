package com.github.fashionbrot.constraint;

import com.github.fashionbrot.validated.constraint.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ObjectBeanConstraintValidator.class})
public @interface ObjectBean {

    //没有任何参数
    String msg() default "~";
}
