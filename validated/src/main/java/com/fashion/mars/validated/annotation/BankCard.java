package com.fashion.mars.validated.annotation;


import java.lang.annotation.*;

/**
 * 验证银行卡
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Mars
public @interface BankCard {

    String msg() default "com.spv.valid.CreditCard.msg";

    /**
     * 正则表达式
     * @return String
     */
    String regexp() default "^([1-9]{1})(\\d{14}|\\d{18})$";

}
