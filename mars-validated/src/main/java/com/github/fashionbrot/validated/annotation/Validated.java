package com.github.fashionbrot.validated.annotation;


import com.github.fashionbrot.validated.groups.DefaultGroup;

import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validated {

    /**
     * 需要校验的 class
     * 默认 全部校验
     * @return Class
     */
    Class<?>[] validClass() default {};

    /**
     * 校验组
     * @return Class
     */
    Class<?>[] groups() default {DefaultGroup.class};

    /**
     * true 快速失败
     * @return boolean
     */
    boolean failFast() default true;

    /**
     * 验证返回值 默认false
     * @return boolean
     */
    boolean validReturnValue() default false;
}
