package com.fashion.spv.validated.constraint;

import java.lang.annotation.Annotation;


public interface ConstraintValidator<A extends Annotation, T> {

    boolean isValid(A annotation, T var1);

}
