package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;

public class ModifyValid implements ConstraintValidator<Default,Object> {

    @Override
    public boolean isValid(Default annotation, Object var1,Class<?> valueType) {
        return false;
    }

    @Override
    public Object defaultValue(Default annotation, Object var, Class valueType) {
        return annotation.value();
    }
}
