package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.*;

/**
 * 验证银行卡
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BankCard {

    String msg() default "com.mars.valid.BankCard.msg";

    /**
     * 正则表达式
     * @return String
     */
    String regexp() default "^([1-9]{1})(\\d{14}|\\d{18})$";

    Class<?>[] groups() default  {};
}
