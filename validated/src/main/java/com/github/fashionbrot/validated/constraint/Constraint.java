package com.github.fashionbrot.validated.constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
    /**
     * 属性 参数 实现接口
     * @return
     */
    Class<? extends ConstraintValidator<? extends Annotation, ?>>[] validatedBy() default {};

    /**
     * 对象bean 实现接口
     * @return
     */
    Class<? extends ConstraintValidatorBean<? extends Annotation, ?>>[] validatedByBean() default {};
}
