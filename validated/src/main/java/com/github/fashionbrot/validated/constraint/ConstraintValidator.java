package com.github.fashionbrot.validated.constraint;

import java.lang.annotation.Annotation;

/**
 *  自定义注解实现接口，调用顺序 isValid,modify,validObject
 * @param <A> Annotation
 * @param <T> T
 */
public  interface ConstraintValidator<A extends Annotation, T> {

    /**
     * annotation all
     * @param annotation annotation
     * @param value value
     * @param valueType valueType
     * @return boolean
     */
    boolean isValid(A annotation, T value,Class<?> valueType);

    /**
     * 修改 value 值
     * @param annotation annotation
     * @param value value
     * @param valueType valueType
     * @return T
     */
    default T modify(A annotation,T value,Class<?> valueType){
        return value;
    }

    /**
     * return value==null?验证通过:验证不通过
     * 验证不过 throw Exception value
     * @param annotation annotation
     * @param value value
     * @param valueType valueType
     * @return String
     */
    default String validObject(A annotation, T value,Class<?> valueType){
        return null;
    }



}
