package com.github.fashionbrot.validated.annotation;




import java.lang.annotation.*;

/**
 * BankCard
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BankCard {

    String msg() default "validated.BankCard.msg";

    /**
     * @return String
     */
    String regexp() default "^([1-9]{1})(\\d{14}|\\d{18})$";

    /**
     * default @see com.github.fashionbrot.validated.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
