package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
@Slf4j
public class MethodUtil {


    private static final String METHOD="get";
    private static final String BOOLEAN_METHOD="is";

    /**
     * 把一个字符串的第一个字母大写、效率是最高的
      */
    public static String getMethodName(String fildeName){
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

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

    public static Object getInstance(Class clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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

    public static Object getMethod(Class clazz, Field field, Object object, String fieldName){
        if (field.getType()==boolean.class){
            return getFieldBooleanMethod(clazz,field,object,fieldName);
        }
        try {
            Method method = clazz.getDeclaredMethod(METHOD+ getMethodName(fieldName),null);
            if (method!=null){
                return method.invoke(object,null);
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public static Object getFieldBooleanMethod(Class clazz,Field field,Object object,String fieldName){
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(BOOLEAN_METHOD+ getMethodName(fieldName),null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (method==null){
            try {
                method = clazz.getDeclaredMethod(METHOD+ getMethodName(fieldName),null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        try {
            return method.invoke(object,null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static boolean checkDeclaredMethod(Class<?> clazz,String method){
        if (clazz!=null){
            Method[] methods = clazz.getDeclaredMethods();
            if (methods!=null && methods.length>0){

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
