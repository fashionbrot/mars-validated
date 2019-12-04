package com.fashion.mars.validated.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证 长度
 * String 类型
 *
 * int
 * long
 * short
 * float
 * double
 *
 * Integer
 * Long
 * Float
 * Double
 * Short
 * String
 *
 */
@Documented
@Target({  FIELD,PARAMETER })
@Retention(RUNTIME)
public @interface Length {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String msg() default "com.spv.valid.Length.msg";

}