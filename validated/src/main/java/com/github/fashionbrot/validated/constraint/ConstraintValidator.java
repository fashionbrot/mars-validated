package com.github.fashionbrot.validated.constraint;

import com.github.fashionbrot.validated.annotation.Default;

import java.lang.annotation.Annotation;


public  interface ConstraintValidator<A extends Annotation, T> {

    /**
     * annotation all
     * @param annotation
     * @param var1
     * @param valueType
     * @return
     */
    boolean isValid(A annotation, T var1,Class<?> valueType);

    /**
     * use @Default
     * @param annotation
     * @param var
     * @param valueType
     * @return
     */
    default Object defaultValue(A annotation, Object var, Class valueType){
        return null;
    }
}
