package com.github.fashionbrot.validated.annotation;



import java.lang.annotation.*;

/**
 * 验证 值大小范围
 * object[]
 * boolean[]
 * byte[]
 * char[]
 * double[]
 * float[]
 * int[]
 * long[]
 * short[]
 * String length
 * Collection
 * Map
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

    long min() default 0;

    long max() default Long.MAX_VALUE;

    String msg() default "validated.Size.msg";

    Class<?>[] groups() default  {};
}
