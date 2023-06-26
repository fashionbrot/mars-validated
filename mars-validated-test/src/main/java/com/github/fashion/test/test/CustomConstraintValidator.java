package com.github.fashion.test.test;


import com.github.fashion.test.service.ValidService;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class CustomConstraintValidator implements ConstraintValidator<Custom, Object> {


    final Environment environment;
    final ValidService validService;

    @Override
    public boolean isValid(Custom custom, Object var1,Class<?> valueType) {
        String test = environment.getProperty("test");
        System.out.println(test);
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
    public Object modify(Custom annotation, Object value, Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+value);
        if (value instanceof CustomModel){
            CustomModel customModel=(CustomModel)value;
            if (customModel!=null){
                customModel.setAbc("在 valid modify 中修改的abc");
            }
            return customModel;
        }
        return value;
    }


    /*public Object modify(Custom annotation, Object var,Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+var);
        if (var instanceof CustomModel){
            CustomModel customModel= (CustomModel) var;
            customModel.setAbc("abc");
            return customModel;
        }
        return var+"1";
    }*/

//    @Override
//    public String validObject(Custom annotation, Object value, Class<?> valueType) {
//        if (value instanceof CustomModel){
//            CustomModel customModel= (CustomModel) value;
//            if (StringUtil.isEmpty(customModel.getAbc())){
//                return "validObject abc is null";
//            }
//            return null;
//        }
//        return null;
//    }
}
