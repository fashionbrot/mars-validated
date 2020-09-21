package com.github.fashion.test.annotation;


import com.github.fashion.test.model.ValidBeanModel;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.constraint.ConstraintValidatorBean;
import com.github.fashionbrot.validated.test.Custom;

public class CustomBeanConstraintValidatorBean implements ConstraintValidatorBean<CustomBean, Object> {



    @Override
    public String isValid(CustomBean custom, Object var1) {

        /**
         * valud
         */
        System.out.println(var1);
        /**
         * return null 则验证成功 其他验证失败
          */
        return null;
    }



    @Override
    public Object modify(CustomBean annotation, Object var) {
        System.out.println("CustomConstraintValidator:"+var);
        if (var instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) var;
            beanModel.setA1("1");
            beanModel.setA2("2");
            return beanModel;
        }
        return var;
    }
}