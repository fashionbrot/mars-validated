package com.github.fashionbrot.validated.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum  AnnotationTypeEnum {

    NOTBLANK("NotBlank"),
    LENGTH("Length"),
    DEFAULT("Default"),
    ASSERTTRUE("AssertTrue"),
    ASSERTFALSE("AssertFalse"),
    CREDITCARD("CreditCard"),
    BANKCARD("BankCard"),
    SIZE("Size"),
    NOTNULL("NotNull"),
    PATTERN("Pattern"),
    EMAIL("Email"),
    PHONE("Phone"),
    IDCARD("IdCard"),
    DIGITS("Digits"),
    NOT_EMPTY("NotEmpty"),
    NOT_EQUAL_SIZE("NotEqualSize");

    private String name;



    private static Map<String,AnnotationTypeEnum> map=new HashMap<>(AnnotationTypeEnum.values().length);

    static {
        AnnotationTypeEnum[] annotationTypeEnums=AnnotationTypeEnum.values();
        for (AnnotationTypeEnum annotationTypeEnum : annotationTypeEnums) {
            map.put(annotationTypeEnum.getName(), annotationTypeEnum);
        }
    }

    public static AnnotationTypeEnum getValue(String key){
        if (map.containsKey(key)){
            return map.get(key);
        }
        return null;
    }

}
