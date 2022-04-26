package com.github.fashionbrot.validated.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

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
    DATE(20,"java.util.Date"),
    OBJECT(21,"java.lang.Object"),
    MULTI_PART_FILE(22,"org.springframework.web.multipart.MultipartFile");


    private static  Map<String,ClassTypeEnum> map=new HashMap<>(ClassTypeEnum.values().length);

    private static Set<String> set = new HashSet<>();
    static {

        Arrays.stream(ClassTypeEnum.values()).forEach(e->{
            set.add(e.getName());
            map.put(e.getName(), e);
        });
    }

    public static boolean checkClass(String str){
        return set.contains(str);
    }

    public static void addClassPath(String classPath){
        if (!set.contains(classPath)){
            set.add(classPath);
        }
    }

    private int type;
    private String name;


    public static ClassTypeEnum getValue(String str){
        if (!map.containsKey(str)){
            return null;
        }
        return map.get(str);
    }

}
