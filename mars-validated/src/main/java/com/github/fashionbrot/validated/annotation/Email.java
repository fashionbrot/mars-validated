package com.github.fashionbrot.validated.annotation;



import java.lang.annotation.*;


/**
 * 验证 是否是邮箱
 * String 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {


    String regexp() default "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    String msg() default "validated.Email.msg";

    /**
     * default @see com.github.fashionbrot.validated.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
