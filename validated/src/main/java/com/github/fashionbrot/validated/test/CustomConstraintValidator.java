package com.github.fashionbrot.validated.test;


import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import org.omg.CORBA.CustomMarshal;

public class CustomConstraintValidator implements ConstraintValidator<Custom, Object> {

    @Override
    public boolean isValid(Custom custom, Object var1,Class<?> valueType) {
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
        return true;
    }

    @Override
    public Object modify(Custom annotation, Object var,Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+var);
        if (var instanceof CustomModel){
            CustomModel customModel= (CustomModel) var;
            customModel.setAbc("abc");
            return customModel;
        }
        return var+"1";
    }
}
