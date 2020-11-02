package com.github.fashion.test.customAnnotation;

import com.github.fashionbrot.validated.constraint.ConstraintValidator;


public class CustomTestConstraintValidator implements ConstraintValidator<CustomAnnotationTest, Object> {

    @Override
    public boolean isValid(CustomAnnotationTest annotation, Object var1,Class<?> valueType) {
        return true;
    }

    @Override
    public Object modify(CustomAnnotationTest annotation, Object var,Class<?> valueType) {
        if ("1".equals(var)){
            return "2";
        }
        return null;
    }
}
