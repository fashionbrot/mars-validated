package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;

public class NotNullConstraint implements ConstraintValidator<NotNull,Object> {


    @Override
    public boolean isValid(NotNull annotation, Object object,Class<?> valueType) {
        return object != null;
    }

}
