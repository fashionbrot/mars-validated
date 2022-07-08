package com.github.fashion.test.annotation;

import com.github.fashionbrot.validated.constraint.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomBeanConstraintValidatorBean.class}) //设置自定义注解实现类
public @interface CustomBean {

    //没有任何参数
    String msg() default "CustomBean 验证失败";

    /**
     * true 快速失败
     * @return boolean
     */
    boolean failFast() default true;
}
