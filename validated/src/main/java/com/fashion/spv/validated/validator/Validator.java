package com.fashion.spv.validated.validator;

import com.fashion.spv.validated.enums.AnnotationTypeEnum;
import com.fashion.spv.validated.enums.ClassTypeEnum;
import com.fashion.spv.validated.validator.support.ParameterType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface Validator {

    /**
     * 入口
     * 获取 接口参数，验证是否含有注解
     * @param method method
     * @param objects parameter
     */
    void parameterAnnotationValid(Method method, Object[] objects);


    /**
     * 验证自定义注解
     * @param annotation annotation
     * @param value      value
     * @param fieldType  fieldType
     * @param fieldName  fieldName
     */
    void checkCustomValid(Annotation annotation, Object value, ClassTypeEnum fieldType, String fieldName);


    /**
     * 验证自定义注解
     * @param annotation annotation impl {com.fashion.spv.validated.constraintConstraintValidator}
     * @param value      value
     * @param fieldType  field type
     * @param fieldName  field name
     */
    void checkCustomValid(Annotation annotation, Object value, String fieldType, String fieldName);




    /**
     * 验证 entity fields 是否包含注解
     * @param valueTypeName  valueTypeName
     * @param clazz          clazz
     * @param objectValue    objectValue
     * @param index          filed index
     */
    void entityFieldsAnnotationValid( String valueTypeName,Class<?> clazz,Object[] objectValue,int index);


    /**
     * 验证基本参数
     * @param parameterType parameterType
     * @param params        params
     * @param index         index
     */
    void validParameter(AnnotationTypeEnum annotationTypeEnum, ParameterType parameterType, Object[] params, int index);


    /**
     * 验证 entityBean fields
     * @param parameterType parameterType
     * @param params        params
     * @param index         index
     */
    void validEntityFields(AnnotationTypeEnum annotationTypeEnum, ParameterType parameterType, Object[] params, int index);

}
