package com.github.fashion.test.annotation;


import com.github.fashion.test.model.ValidBeanModel;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashion.test.test.CustomModel;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.github.fashionbrot.validated.util.ObjectUtil;
import lombok.Data;

import java.util.StringJoiner;

public class CustomBeanConstraintValidatorBean implements ConstraintValidator<CustomBean, Object> {

    @Override
    public boolean isValid(CustomBean annotation, Object value, Class<?> valueType) {
        if (value instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) value;
            StringJoiner msg=new StringJoiner(",");
            if (ObjectUtil.isEmpty(beanModel.getA1())){
                msg.add("A1 不能为空") ;
                if (annotation.failFast()){
                    ValidatedException.throwMsg("A1",msg.toString(),null,null,null);
                }
            }
            if (ObjectUtil.isEmpty(beanModel.getA2())){
                msg.add("A2 不能为空") ;
                if (annotation.failFast()){
                    ValidatedException.throwMsg("A2",msg.toString(),null,null,null);
                }
            }
            if (msg.length()>0){
                ValidatedException.throwMsg("req",msg.toString(),null,null,null);
            }
        }
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

}
