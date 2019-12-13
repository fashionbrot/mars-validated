package com.github.fashionbrot.validated.util;


import com.github.fashionbrot.validated.enums.ClassTypeEnum;

public class ClassTypeUtil {



    public static ClassTypeEnum getClassTypeEnum(String valueTypeName){
        ClassTypeEnum classTypeEnum= ClassTypeEnum.getValue(valueTypeName);
        return classTypeEnum;
    }



}
