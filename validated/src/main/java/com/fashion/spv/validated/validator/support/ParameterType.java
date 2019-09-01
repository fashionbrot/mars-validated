package com.fashion.spv.validated.validator.support;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

@Data
public class ParameterType {


    private Object value;

    private String annotationClassName;

    private Parameter parameter;

    private Field field;

    private AnnotationCustom annotationCustom;


    private String fieldName;

    public ParameterType(Object value, Field field, AnnotationCustom annotationCustom) {
        this.value = value;
        this.field = field;
        this.annotationCustom = annotationCustom;
        fieldName = field.getName();
    }

    public ParameterType(String methodParameterName, Object value, Parameter parameter, AnnotationCustom annotationCustom) {
        this.value = value;
        this.parameter = parameter;
        this.annotationCustom = annotationCustom;
        this.fieldName = methodParameterName;

    }
}
