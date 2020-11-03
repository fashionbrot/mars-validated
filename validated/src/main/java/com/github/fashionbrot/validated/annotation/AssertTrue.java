package com.github.fashionbrot.validated.annotation;

import java.lang.annotation.*;

/**
 * 验证是否为 true
 * boolean
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertTrue {

    String msg() default "com.mars.valid.AssertTrue.msg";

    Class<?>[] groups() default  {};
}
