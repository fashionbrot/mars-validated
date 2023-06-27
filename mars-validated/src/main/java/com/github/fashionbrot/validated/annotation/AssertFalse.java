package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.*;

/**
 * 验证是否为false
 * boolean
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertFalse {

    String msg() default "validated.AssertFalse.msg";

    /**
     * default @see com.github.fashionbrot.validated.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
