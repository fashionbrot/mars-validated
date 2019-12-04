package com.fashion.mars.validated.annotation;


import java.lang.annotation.*;

/**
 * 验证信用卡
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreditCard {

    String msg() default "com.spv.valid.CreditCard.msg";

    /**
     * 正则表达式
     * @return String
     */
    String regexp() default "(?:3[47][0-9]{13})|(?:3(?:0[0-5]|[68][0-9])[0-9]{11})|(?:6(?:011|5[0-9]{2})(?:[0-9]{12}))|(?:(?:2131|1800|35\\\\d{3})\\\\d{11})|(?:(?:5[0678]\\\\d\\\\d|6304|6390|67\\\\d\\\\d)\\\\d{8,15})|(?:(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12})|(?:4[0-9]{12})(?:[0-9]{3})?";
}
