package com.github.fashionbrot.validated.annotation;


import com.github.fashionbrot.validated.groups.DefaultGroup;

import java.lang.annotation.*;

/**
 * 验证 字符串是否为空
 *
 * String 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

    String  msg() default "com.mars.valid.NotBlank.msg";

    Class<?>[] groups() default  {DefaultGroup.class};
}
