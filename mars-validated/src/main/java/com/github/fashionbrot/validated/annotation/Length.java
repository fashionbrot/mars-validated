package com.github.fashionbrot.validated.annotation;



import java.lang.annotation.*;

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
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String msg() default "validated.Length.msg";

    Class<?>[] groups() default  {};
}
