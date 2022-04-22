package com.github.fashion.test.annotation;


import com.github.fashion.test.model.ValidBeanModel;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashion.test.test.CustomModel;

public class CustomBeanConstraintValidatorBean implements ConstraintValidator<CustomBean, Object> {



    @Override
    public boolean isValid(CustomBean annotation, Object value, Class<?> valueType) {
        return true;
    }

    @Override
    public Object modify(CustomBean annotation, Object value, Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+value);
        if (value instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) value;
            beanModel.setA1("1");
            beanModel.setA2("2");
            return beanModel;
        }if (value instanceof CustomModel){
            CustomModel customModel=(CustomModel)value;
            if (customModel!=null){
                customModel.setAbc("在 valid modify 中修改的abc");
            }
            return customModel;
        }
        return value;
    }

    @Override
    public String validObject(CustomBean annotation, Object value, Class<?> valueType) {
        if (value instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) value;
            if (beanModel!=null && (beanModel.getA1()==null || beanModel.getA2()==null)){
                return "a1 或者 a2 为空";
            }
        }
        /**
         * return null 则验证成功 其他验证失败
         */
        return null;
    }


}
