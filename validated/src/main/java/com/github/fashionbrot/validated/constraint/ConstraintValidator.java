package com.github.fashionbrot.validated.constraint;

import java.lang.annotation.Annotation;

/**
 *  自定义注解实现接口，调用顺序 isValid,modify,validObject
 * @param <A>
 * @param <T>
 */
public  interface ConstraintValidator<A extends Annotation, T> {

    /**
     * annotation all
     * @param annotation
     * @param value
     * @param valueType
     * @return
     */
    boolean isValid(A annotation, T value,Class<?> valueType);

    /**
     * 修改 value 值
     * @param annotation
     * @param value
     * @param valueType
     * @return
     */
    default T modify(A annotation,T value,Class<?> valueType){
        return value;
    }

    /**
     * return value==null?验证通过:验证不通过
     * 验证不过 throw Exception value
     * @param annotation
     * @param value
     * @param valueType
     * @return
     */
    default String validObject(A annotation, T value,Class<?> valueType){
        return null;
    }



}
