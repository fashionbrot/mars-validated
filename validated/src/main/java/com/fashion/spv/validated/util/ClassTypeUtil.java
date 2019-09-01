package com.fashion.spv.validated.util;


import com.fashion.spv.validated.enums.ClassTypeEnum;

public class ClassTypeUtil {



    public static ClassTypeEnum getClassTypeEnum(String valueTypeName){
        ClassTypeEnum classTypeEnum= ClassTypeEnum.getValue(valueTypeName);
        return classTypeEnum;
    }



}
