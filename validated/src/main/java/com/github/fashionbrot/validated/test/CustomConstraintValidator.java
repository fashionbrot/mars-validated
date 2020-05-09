package com.github.fashionbrot.validated.test;


import com.github.fashionbrot.validated.constraint.ConstraintValidator;

public class CustomConstraintValidator implements ConstraintValidator<Custom, Object> {

    @Override
    public boolean isValid(Custom custom, Object var1) {
        /**
         * 自定义方法
         */
        int min=custom.min();
        /**
         * valud
         */
        System.out.println(var1);
        var1="567";
        /**
         * return true 则验证成功 false 验证失败
          */
        return false;
    }

    @Override
    public Object modify(Custom annotation, Object var) {
        System.out.println("CustomConstraintValidator:"+var);
        return var+"1";
    }
}
