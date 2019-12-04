package com.fashion.mars.validated.annotation;


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
@Mars
public @interface Default {

    String  value() ;

}
