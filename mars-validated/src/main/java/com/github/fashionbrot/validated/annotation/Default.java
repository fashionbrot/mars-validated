package com.github.fashionbrot.validated.annotation;


import java.lang.annotation.*;

/**
 * 是否设置默认值
 *
 * Integer
 * Double
 * Long
 * Short
 * Float
 * BigDecimal
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {

    String  value() ;

    Class<?>[] groups() default  {};
}
