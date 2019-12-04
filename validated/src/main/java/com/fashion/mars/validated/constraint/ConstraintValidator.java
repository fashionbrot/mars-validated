package com.fashion.mars.validated.constraint;

import java.lang.annotation.Annotation;


public interface ConstraintValidator<A extends Annotation, T> {

    boolean isValid(A annotation, T var1);

}
