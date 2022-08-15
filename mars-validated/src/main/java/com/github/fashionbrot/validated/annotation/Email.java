package com.github.fashionbrot.validated.annotation;

import com.github.fashionbrot.validated.groups.DefaultGroup;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证 是否是邮箱
 * String 类型
 */
@Documented
@Target({  FIELD,PARAMETER })
@Retention(RUNTIME)
public @interface Email {


    String regexp() default "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    String msg() default "com.mars.valid.Email.msg";

    Class<?>[] groups() default  {DefaultGroup.class};
}
