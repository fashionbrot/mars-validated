package com.fashion.mars.validated.util;


import com.fashion.mars.validated.enums.ClassTypeEnum;

public class ClassTypeUtil {



    public static ClassTypeEnum getClassTypeEnum(String valueTypeName){
        ClassTypeEnum classTypeEnum= ClassTypeEnum.getValue(valueTypeName);
        return classTypeEnum;
    }



}
