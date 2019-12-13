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
        /**
         * return true 则验证成功 false 验证失败
          */
        return true;
    }
}
