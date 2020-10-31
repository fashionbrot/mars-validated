package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validated {

    /**
     * 需要校验的 class
     * 默认 全部校验
     */
    Class<?>[] validClass() default {};

    /**
     * 校验组
     * @return
     */
    Class<?>[] groups() default { };

    /**
     * true 快速失败
     * @return
     */
    boolean failFast() default true;

}
