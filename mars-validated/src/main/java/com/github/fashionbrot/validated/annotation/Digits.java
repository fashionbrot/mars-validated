package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.*;


/**
 * 验证 是否为数字
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Digits {


    String msg() default "validated.Digits.msg";

    Class<?>[] groups() default  {};
}
