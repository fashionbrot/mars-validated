package com.github.fashionbrot.validated.constraint;

import java.lang.annotation.Annotation;


public  interface ConstraintValidatorBean<A extends Annotation, T> {

    /**
     * return value==null?验证通过:验证不通过
     * 验证不过 throw Exception value
     * @param annotation
     * @param var1
     * @return
     */
    String isValid(A annotation, T var1);

    /**
     * 修改 var 值
     * @param annotation
     * @param var
     * @return
     */
    default T modify(A annotation,T var){
        return var;
    }

}
