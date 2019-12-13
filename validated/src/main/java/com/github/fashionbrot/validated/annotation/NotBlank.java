package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.*;

/**
 * 验证 字符串是否为空
 *
 * String 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Mars
public @interface NotBlank {

    String  msg() default "com.spv.valid.NotBlank.msg";

}
