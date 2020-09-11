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
@Mars
public @interface NotNull {

    String  msg() default "com.spv.valid.NotNull.msg";

    Class<?>[] groups() default  {};
}
