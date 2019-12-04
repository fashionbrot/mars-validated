package com.fashion.mars.validated.util;

import com.fashion.mars.validated.enums.ClassTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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


    public static Method getMethod(Class clazz, String methodName,Class<?>... parameterTypes){

        try {
            return   clazz.getDeclaredMethod(methodName,parameterTypes);
        } catch (NoSuchMethodException e) {
            log.error(" NoSuchMethodException getMethod clazz:"+clazz.getTypeName() +" methodName:"+methodName +"  parameterTypesLength:"+parameterTypes.length,e);
        }

        return null;
    }

    public static boolean invokeMethod(Method method, Object object, Annotation annotation, Object paramValue, ClassTypeEnum classTypeEnum){
        try {
            if (method!=null){
                return (boolean) method.invoke(object,annotation,paramValue,classTypeEnum);
            }
        } catch (IllegalAccessException e) {
            log.error(" IllegalAccessException invokeMethod method:"+method.getName() +" annotationType:"+annotation.annotationType().getTypeName(),e);
            return true;
        } catch (InvocationTargetException e) {
            log.error(" InvocationTargetException invokeMethod method:"+method.getName() +" annotationType:"+annotation.annotationType().getTypeName(),e);
            return true;
        }
        return true;
    }

    public static  Method getAnnotationTypeMethod(Class<? extends Annotation> annotationType,String methodName,Class<?>... parameterTypes){
        Method method=null;
        try {
            if (annotationType!=null) {
                method = annotationType.getDeclaredMethod(methodName, parameterTypes);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public static Object invokeMethod(Method method,Annotation annotation,Class<?>... parameterTypes){
        try {
            if (method!=null) {
                return method.invoke(annotation, parameterTypes);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
