package com.fashion.mars.validated.util;


import com.fashion.mars.validated.enums.AnnotationTypeEnum;

import java.util.regex.Pattern;

public class AnnotationUtil {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile("com.fashion.spv.validated.annotation");


    public static AnnotationTypeEnum getAnnotationName(String annotationName, String annotationNamePath){
        if(!PACKAGE_PATTERN.matcher(annotationNamePath).lookingAt()){
            return null;
        }
        return AnnotationTypeEnum.getValue(annotationName);
    }


}
