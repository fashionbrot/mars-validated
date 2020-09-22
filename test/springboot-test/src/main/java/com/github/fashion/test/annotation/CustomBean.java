package com.github.fashion.test.annotation;

import com.github.fashionbrot.validated.constraint.Constraint;
import lombok.Data;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedByBean = {CustomBeanConstraintValidatorBean.class})
public @interface CustomBean {

    //没有任何参数
}
