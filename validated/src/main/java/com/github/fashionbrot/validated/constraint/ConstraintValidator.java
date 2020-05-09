package com.github.fashionbrot.validated.constraint;

import java.lang.annotation.Annotation;


public  interface ConstraintValidator<A extends Annotation, T> {

    boolean isValid(A annotation, T var1);


    default T modify(A annotation,T var){
        return var;
    }

}
