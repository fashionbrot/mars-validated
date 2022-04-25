package com.github.fashionbrot.validated.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEqualLength {

    int length() default 0;

    String msg() default "com.mars.valid.NotEqualLength.msg";

    Class<?>[] groups() default  {};
}
