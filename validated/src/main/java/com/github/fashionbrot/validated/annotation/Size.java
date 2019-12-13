package com.github.fashionbrot.validated.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证 值大小范围
 * int
 * long
 * short
 * Integer
 * Long
 * Short
 */
@Documented
@Target({  FIELD,PARAMETER })
@Retention(RUNTIME)
@Mars
public @interface Size {

    long min() default 0;

    long max() default Long.MAX_VALUE;

    String msg() default "com.spv.valid.Size.msg";
}
