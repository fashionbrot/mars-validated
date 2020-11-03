package com.github.fashionbrot.validated.annotation;

import java.lang.annotation.*;

/**
 * 验证是否为false
 * boolean
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertFalse {

    String msg() default "com.mars.valid.AssertFalse.msg";

    Class<?>[] groups() default  {};
}
