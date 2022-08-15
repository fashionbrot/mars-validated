package com.github.fashionbrot.validated.annotation;

import com.github.fashionbrot.validated.groups.DefaultGroup;

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

    Class<?>[] groups() default  {DefaultGroup.class};
}
