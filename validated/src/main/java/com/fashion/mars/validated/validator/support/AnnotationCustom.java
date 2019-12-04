package com.fashion.mars.validated.validator.support;

import java.lang.annotation.Annotation;

public interface AnnotationCustom {

    /**
     * 根据 parmaeter 获取 参数
     * 或者跟 field 获取属性
     *
     * @param annotationClass annotationClass
     * @param <T>   annotationClass
     * @return  Annotation
     */
    <T extends Annotation> T getDeclaredAnnotation(ParameterType parameterType, Class<T> annotationClass);


    /**
     * 设置默认值
     *
     * @param parameterType parameterType
     * @param defaultValue  defaultValue
     * @param objects       objects
     * @param index         index
     */
    void setDefault(ParameterType parameterType, Object defaultValue, Object[] objects, int index);


}
