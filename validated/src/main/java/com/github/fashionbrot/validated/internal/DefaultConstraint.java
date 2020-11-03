package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;

public class DefaultConstraint implements ConstraintValidator<Default,Object> {

    @Override
    public boolean isValid(Default annotation, Object var1,Class<?> valueType) {
        return true;
    }

    @Override
    public Object modify(Default annotation, Object value, Class<?> valueType) {
        return annotation.value();
    }
}
