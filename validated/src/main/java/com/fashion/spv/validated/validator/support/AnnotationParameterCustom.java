package com.fashion.spv.validated.validator.support;

import com.fashion.spv.validated.enums.ClassTypeEnum;
import com.fashion.spv.validated.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

@Slf4j
public class AnnotationParameterCustom implements AnnotationCustom {



    @Override
    public <T extends Annotation> T getDeclaredAnnotation(ParameterType parameterType, Class<T> annotationClass) {
        return parameterType.getParameter().getDeclaredAnnotation(annotationClass);
    }

    @Override
    public void setDefault(ParameterType parameterType, Object defaultValue, Object[] objects, int index) {
        if (defaultValue != null && StringUtil.isNotBlank(defaultValue.toString())) {
            ClassTypeEnum classTypeEnum = ClassTypeEnum.getValue(parameterType.getParameter().getType().getTypeName());
            try {
                if (classTypeEnum != null) {
                    switch (classTypeEnum) {
                        case PACK_BOOLEAN:
                            objects[index] = StringUtil.formatBoolean(defaultValue);
                            break;
                        case PACK_INT:
                            objects[index] = StringUtil.getIntValue(defaultValue);
                            break;
                        case PACK_LONG:
                            objects[index] = StringUtil.getLongValue(defaultValue);
                            break;
                        case PACK_DOUBLE:
                            objects[index] = StringUtil.getDoubleValue(defaultValue);
                            break;
                        case PACK_FLOAT:
                            objects[index] = StringUtil.getFloatValue(defaultValue);
                            break;
                        case PACK_SHORT:
                            objects[index] = StringUtil.getShortValue(defaultValue);
                            break;
                        case BIGDECIMAL:
                            objects[index] = StringUtil.getBigDecimalValue(defaultValue);
                            break;
                        case STRING:
                            objects[index] = defaultValue;
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                log.error(" setDefault error fileName:{}", parameterType.getParameter().getName());
            }
        }
    }
}
