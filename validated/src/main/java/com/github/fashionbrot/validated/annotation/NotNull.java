package com.github.fashionbrot.validated.annotation;

import java.lang.annotation.*;

/**
 * 验证 是否为空
 *
 * Object 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    String  msg() default "com.mars.valid.NotNull.msg";

    Class<?>[] groups() default  {};
}
