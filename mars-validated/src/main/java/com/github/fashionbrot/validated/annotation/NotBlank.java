package com.github.fashionbrot.validated.annotation;




import java.lang.annotation.*;

/**
 * 验证 字符串是否为空
 *
 * String 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

    String  msg() default "validated.NotBlank.msg";

    Class<?>[] groups() default  {};
}
