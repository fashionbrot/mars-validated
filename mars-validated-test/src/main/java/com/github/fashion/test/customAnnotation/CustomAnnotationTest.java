package com.github.fashion.test.customAnnotation;

import com.github.fashionbrot.validated.constraint.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomTestConstraintValidator.class})
public @interface CustomAnnotationTest {

    String msg() default "自定义注解 msg";

}
