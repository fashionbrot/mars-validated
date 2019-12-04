package com.fashion.mars.validated.annotation;

import java.lang.annotation.*;

/**
 * 验证 字符串是否为空
 *
 * String 类型
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Mars
public @interface NotEmpty {

    String msg() default  "com.spv.valid.NotEmpty.msg";
}
