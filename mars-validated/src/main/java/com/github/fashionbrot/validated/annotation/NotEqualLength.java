package com.github.fashionbrot.validated.annotation;



import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})//issue#7
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEqualLength {

    int length() default 0;

    String msg() default "validated.NotEqualLength.msg";

    Class<?>[] groups() default  {};
}
