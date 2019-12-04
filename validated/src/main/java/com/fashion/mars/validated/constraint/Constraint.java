package com.fashion.mars.validated.constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {

    Class<? extends ConstraintValidator<? extends Annotation, ?>>[] validatedBy();

}
