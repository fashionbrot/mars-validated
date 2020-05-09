package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.enums.AnnotationTypeEnum;
import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import com.github.fashionbrot.validated.validator.support.AnnotationCustom;
import com.github.fashionbrot.validated.validator.support.ParameterType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
     * 验证自定义注解 params
     * @param annotation annotation
     * @param fieldValue fieldValue
     * @param params     params
     * @param index      index
     * @param fieldType  fieldType
     * @param fieldName  fieldName
     * @param field     field
     */
    void checkCustomValid(Annotation annotation,Object fieldValue, Object[] params, int index, ClassTypeEnum fieldType, String fieldName, Field field);


    /**
     * 验证自定义注解
     * @param annotation annotation impl {com.fashion.spv.validated.constraintConstraintValidator}
     * @param fieldValue      value
     * @param fieldType  field type
     * @param fieldName  field name
     * @param params     params
     * @param index      index
     * @param field      field
     */
    void checkCustomValid(Annotation annotation, Object fieldValue, String fieldType, String fieldName,Object[] params,int index,Field field);




    /**
     * 验证 entity fields 是否包含注解
     * @param validated      validated
     * @param valueTypeName  valueTypeName
     * @param clazz          clazz
     * @param objectValue    objectValue
     * @param index          filed index
     */
    void entityFieldsAnnotationValid(Validated validated, String valueTypeName, Class<?> clazz, Object[] objectValue, int index);


    /**
     * Verify basic parameters
     * @param annotationTypeEnum annotationTypeEnum
     * @param parameterType parameterType
     * @param params        params
     * @param index         index
     */
    void validParameter(AnnotationTypeEnum annotationTypeEnum, ParameterType parameterType, Object[] params, int index);


    /**
     * valid entityBean fields
     * @param annotationTypeEnum annotationTypeEnum
     * @param parameterType      parameterType
     * @param params            params
     * @param index         index
     */
    void validEntityFields(AnnotationTypeEnum annotationTypeEnum, ParameterType parameterType, Object[] params, int index);

}
