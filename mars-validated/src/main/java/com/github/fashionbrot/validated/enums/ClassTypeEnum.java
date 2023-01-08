package com.github.fashionbrot.validated.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum ClassTypeEnum {

    BOOLEAN("boolean"),
    CHAR("char"),
    BYTE("byte"),
    SHORT("short"),
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    NUMBER("java.lang.Number"),
    SQL_TIMESTAMP("java.sql.Timestamp"),
    SQL_TIME("java.sql.Time"),
    SQL_DATE("java.sql.Date"),
    PACK_BOOLEAN("java.lang.Boolean"),
    PACK_CHAR("java.lang.Character"),
    PACK_BYTE("java.lang.Byte"),
    PACK_SHORT("java.lang.Short"),
    PACK_INT("java.lang.Integer"),
    PACK_LONG("java.lang.Long"),
    PACK_FLOAT("java.lang.Float"),
    PACK_DOUBLE("java.lang.Double"),
    STRING("java.lang.String"),
    BIG_DECIMAL("java.math.BigDecimal"),
    BIG_INTEGER("java.math.BigInteger"),
    DATE("java.util.Date"),
    YEAR("java.time.Year"),
    PERIOD("java.time.Period"),
    YEAR_MONTH("java.time.YearMonth"),
    MONTH_DAY ("java.time.MonthDay"),
    LOCAL_TIME("java.time.LocalTime"),
    LOCAL_DATE("java.time.LocalDate"),
    LOCAL_DATE_TIME("java.time.LocalDateTime"),
    ;


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

    private String name;


    public static ClassTypeEnum getValue(String str){
        if (!map.containsKey(str)){
            return null;
        }
        return map.get(str);
    }

}
