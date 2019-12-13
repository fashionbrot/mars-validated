package com.github.fashionbrot.validated.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Mars
public @interface NotEqualSize {

    int size() default 0;

    String msg() default "com.spv.valid.NotEqualSize.msg";
}
