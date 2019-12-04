package com.fashion.mars.validated.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ClassTypeEnum {

    BOOLEAN(1,"boolean"),
    CHAR(2,"char"),
    BYTE(3,"byte"),
    SHORT(4,"short"),
    INT(5,"int"),
    LONG(6,"long"),
    FLOAT(7,"float"),
    DOUBLE(8,"double"),

    PACK_BOOLEAN(9,"java.lang.Boolean"),
    PACK_CHAR(10,"java.lang.Character"),
    PACK_BYTE(11,"java.lang.Byte"),
    PACK_SHORT(12,"java.lang.Short"),
    PACK_INT(13,"java.lang.Integer"),
    PACK_LONG(14,"java.lang.Long"),
    PACK_FLOAT(15,"java.lang.Float"),
    PACK_DOUBLE(16,"java.lang.Double"),

    STRING(17,"java.lang.String"),
    BIGDECIMAL(18,"java.math.BigDecimal"),
    BIGIINTEGER(19,"java.math.BigInteger"),
    //DATE(20,"java.util.Date"),
    OBJECT(21,"java.lang.Object");


    private static  Map<String,ClassTypeEnum> map=new HashMap<>(ClassTypeEnum.values().length);
    static {

        ClassTypeEnum[] values= ClassTypeEnum.values();
        for (ClassTypeEnum classTypeEnum : values) {
            map.put(classTypeEnum.getName(), classTypeEnum);
        }
    }

    public static ClassTypeEnum getValue(String str){
       if (!map.containsKey(str)){
           return null;
       }
       return map.get(str);
    }

    private int type;
    private String name;



}
