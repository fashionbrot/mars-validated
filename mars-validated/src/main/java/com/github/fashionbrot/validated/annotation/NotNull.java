package com.github.fashionbrot.validated.annotation;

import com.github.fashionbrot.validated.groups.DefaultGroup;

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

    Class<?>[] groups() default  {DefaultGroup.class};
}
