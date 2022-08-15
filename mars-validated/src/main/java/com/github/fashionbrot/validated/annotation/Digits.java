package com.github.fashionbrot.validated.annotation;


import com.github.fashionbrot.validated.groups.DefaultGroup;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证 是否为数字
 * String
 */
@Documented
@Target({  FIELD,PARAMETER })
@Retention(RUNTIME)
public @interface Digits {


    String msg() default "com.mars.valid.Digits.msg";

    Class<?>[] groups() default  {DefaultGroup.class};
}
