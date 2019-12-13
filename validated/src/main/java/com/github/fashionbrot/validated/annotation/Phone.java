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


    String regexp() default "^(((13[0-9])|(14[579])|(15[^4,\\D])|(18[0-9])|(17[0-9]))|(19[8,9])|166)\\d{8}$";

    String msg() default "com.spv.valid.Phone.msg";

}
