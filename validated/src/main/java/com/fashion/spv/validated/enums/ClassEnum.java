package com.fashion.spv.validated.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ClassEnum {

    BOOLEAN("boolean",boolean.class),
    CHAR("char",char.class),
    BYTE("byte",byte.class),
    SHORT("short",short.class),
    INT("int",int.class),
    LONG("long",long.class),
    FLOAT("float",float.class),
    DOUBLE("double",double.class),

    PACK_BOOLEAN("java.lang.Boolean",Boolean.class),
    PACK_CHAR("java.lang.Character",Character.class),
    PACK_BYTE("java.lang.Byte",Byte.class),
    PACK_SHORT("java.lang.Short",Short.class),
    PACK_INT("java.lang.Integer",Integer.class),
    PACK_LONG("java.lang.Long",Long.class),
    PACK_FLOAT("java.lang.Float",Float.class),
    PACK_DOUBLE("java.lang.Double",Double.class),

    STRING("java.lang.String",String.class),
    BIGDECIMAL("java.math.BigDecimal",BigDecimal.class),
    BIGINTEGER("java.math.BigInteger",BigInteger.class),
    DATE("java.util.Date",Date.class);


    private static  Map<String,ClassEnum> map=new HashMap<>(ClassEnum.values().length);
    static {

        ClassEnum[] values= ClassEnum.values();
        for (ClassEnum classTypeEnum : values) {
            map.put(classTypeEnum.getName(), classTypeEnum);
        }
    }

    public static ClassEnum getValue(String str){
       if (!map.containsKey(str)){
           return null;
       }
       return map.get(str);
    }


    private String name;
    private Class clazz;


}
