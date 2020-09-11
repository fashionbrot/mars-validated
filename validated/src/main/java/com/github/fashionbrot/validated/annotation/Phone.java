package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证是否是手机号
 * String 类型
 */
@Documented
@Target({  FIELD,PARAMETER })
@Retention(RUNTIME)
@Mars
public @interface Phone {


    String regexp() default "^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0125689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[89]\\d)\\d{7}$";

    String msg() default "com.spv.valid.Phone.msg";

    Class<?>[] groups() default  {};
}
