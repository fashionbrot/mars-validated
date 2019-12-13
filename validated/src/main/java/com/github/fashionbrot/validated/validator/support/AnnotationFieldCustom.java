package com.github.fashionbrot.validated.validator.support;


import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import com.github.fashionbrot.validated.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Slf4j
public class AnnotationFieldCustom implements AnnotationCustom {

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(ParameterType parameterType, Class<T> annotationClass) {
        return parameterType.getField().getDeclaredAnnotation(annotationClass);
    }

    @Override
    public void setDefault(ParameterType parameterType, Object defaultValue, Object[] objects, int index) {
        if (defaultValue != null && StringUtil.isNotBlank(defaultValue.toString())) {
            Field field = parameterType.getField();
            Object object = objects[index];

            ClassTypeEnum classTypeEnum = ClassTypeEnum.getValue(parameterType.getField().getType().getName());
            try {
                if (classTypeEnum != null) {
                    switch (classTypeEnum) {
                        case PACK_BOOLEAN:
                            field.set(object, StringUtil.formatBoolean(defaultValue));
                            break;
                        case PACK_INT:
                            field.set(object, StringUtil.getIntValue(defaultValue));
                            break;
                        case PACK_LONG:
                            field.set(object, StringUtil.getLongValue(defaultValue));
                            break;
                        case PACK_DOUBLE:
                            field.set(object, StringUtil.getDoubleValue(defaultValue));
                            break;
                        case PACK_FLOAT:
                            field.set(object, StringUtil.getFloatValue(defaultValue));
                            break;
                        case PACK_SHORT:
                            field.set(object, StringUtil.getShortValue(defaultValue));
                            break;
                        case BIGDECIMAL:
                            field.set(object, StringUtil.getBigDecimalValue(defaultValue));
                            break;
                        case STRING:
                            field.set(object, defaultValue);
                            break;
                        default:
                            break;
                    }
                    return;
                }
            } catch (Exception e) {
                log.error("1 setDefault field error fieldTypeName:" + parameterType.getField().getName() + " defaultValue:" + defaultValue);
            }
            if (object != null) {
                try {
                    field.set(object, defaultValue);
                } catch (Exception e) {
                    log.error("2 setDefault field error fieldTypeName:" + parameterType.getField().getName() + " defaultValue:" + defaultValue);
                }
            }
        }
    }


}
