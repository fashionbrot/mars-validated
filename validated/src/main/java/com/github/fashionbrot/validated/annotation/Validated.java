package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validated {

    /**
     * 默认 全部校验
     * @return
     */
    Class<?>[] validClass() default {};

}
