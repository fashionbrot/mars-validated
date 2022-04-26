package com.github.fashionbrot.validated.annotation;

import java.lang.annotation.*;

/**
 * 验证 字符串是否为空
 *
 * String 类型
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {

    String msg() default  "com.mars.valid.NotEmpty.msg";

    Class<?>[] groups() default  {};
}
