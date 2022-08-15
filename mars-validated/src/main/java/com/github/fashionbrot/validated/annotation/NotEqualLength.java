package com.github.fashionbrot.validated.annotation;

import com.github.fashionbrot.validated.groups.DefaultGroup;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEqualLength {

    int length() default 0;

    String msg() default "com.mars.valid.NotEqualLength.msg";

    Class<?>[] groups() default  {DefaultGroup.class};
}
