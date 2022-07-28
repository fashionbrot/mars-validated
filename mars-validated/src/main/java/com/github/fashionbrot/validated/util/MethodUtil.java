package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
@Slf4j
public class MethodUtil {


    public static Object getFieldValue(Field field,Object object){
        if (field!=null && !Modifier.isStatic(field.getModifiers())){
            //打开私有访问
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static ConstraintValidator newInstance(Class<? extends ConstraintValidator> constraint){
        try {
            return constraint.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean checkDeclaredMethod(Class<?> clazz,String method){
        if (clazz!=null){
            Method[] methods = clazz.getDeclaredMethods();
            if (ObjectUtil.isNotEmpty(methods)){
                for(int i=0;i<methods.length;i++){
                    if (methods[i].getName().equals(method)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
