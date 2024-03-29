package com.github.fashionbrot.validated.annotation;


import com.github.fashionbrot.validated.groups.DefaultGroup;

import java.lang.annotation.*;

/**
 * BankCard
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BankCard {

    String msg() default "com.mars.valid.BankCard.msg";

    /**
     * @return String
     */
    String regexp() default "^([1-9]{1})(\\d{14}|\\d{18})$";

    Class<?>[] groups() default  {DefaultGroup.class};
}
