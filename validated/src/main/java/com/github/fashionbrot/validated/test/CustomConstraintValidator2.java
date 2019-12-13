package com.github.fashionbrot.validated.test;


import com.github.fashionbrot.validated.constraint.ConstraintValidator;

public class CustomConstraintValidator2 implements ConstraintValidator<Custom, Object> {



    @Override
    public boolean isValid(Custom custom, Object var1) {
        Class customStringClass=this.getClass();

        System.out.println();
        System.out.println(var1);
        return false;
    }


}
