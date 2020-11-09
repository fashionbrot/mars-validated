package com.github.fashion.test.test;


import com.github.fashionbrot.validated.constraint.ConstraintValidator;

public class CustomConstraintValidator2 implements ConstraintValidator<Custom, Object> {

    @Override
    public boolean isValid(Custom annotation, Object var1,Class<?> valueType) {
        return true;
    }

}
