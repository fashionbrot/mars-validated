package com.github.fashionbrot.validated.annotation;



import java.lang.annotation.*;


/**
 * 验证正则表达式
 * String 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pattern {

    String regexp();

    String msg();

    Class<?>[] groups() default  {};
}
