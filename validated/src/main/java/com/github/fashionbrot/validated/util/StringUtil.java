package com.github.fashionbrot.validated.util;


import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

@Slf4j
public final class StringUtil {

    private static final String EMPTY = "";




    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String formatString(Object object){
        if (object==null){
            return EMPTY;
        }else{
            return object.toString();
        }
    }

    public static boolean isDigits(String str) {
        return isNumeric(str);
    }

    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    private static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                         final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String)cs).regionMatches(ignoreCase, thisStart, (String)substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        // Extract these first so we detect NPEs the same as the java.lang.String version
        final int srcLen = cs.length() - thisStart;
        final int otherLen = substring.length() - start;

        // Check for invalid parameters
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder buf = new StringBuilder(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    public static int getIntValue(Object object){
        if (object==null){
            return 0;
        }else{
            String str=object.toString();
            if (!isNumeric(str)){
                return 0;
            }else{
                try {
                    return Integer.valueOf(str);
                }catch (Exception e){
                    return 0;
                }
            }
        }
    }

    public static long getLongValue(Object object){
        if (object==null){
            return 0;
        }else{
            String str=object.toString();
            if (!isNumeric(str)){
                return 0;
            }else{
                try {
                    return Long.valueOf(str);
                }catch (Exception e){
                    return 0;
                }
            }
        }
    }

    public static double getDoubleValue(Object object){
        if (object==null){
            return 0;
        }else{
            try {
                return Double.valueOf(object.toString());
            }catch (Exception e){
                return 0;
            }
        }
    }

    public static float getFloatValue(Object object){
        if (object==null){
            return 0;
        }else{
            try {
                return Float.valueOf(object.toString());
            }catch (Exception e){
                return 0;
            }
        }
    }

    public static short getShortValue(Object object){
        if (object==null){
            return 0;
        }else{
            try {
                return Short.valueOf(object.toString());
            }catch (Exception e){
                return 0;
            }
        }
    }

    public static BigDecimal getBigDecimalValue(Object object){
        if (object==null){
            return BigDecimal.ZERO;
        }else{
            try {
                return new BigDecimal(object.toString());
            }catch (Exception e){
                return BigDecimal.ZERO;
            }
        }
    }


    public static boolean formatBoolean(Object object){
        if (object==null){
            return false;
        }else{
            String str=object.toString();
            try {
                return Boolean.valueOf(str);
            }catch (Exception e){
                return false;
            }
        }
    }

    public static Object formatObject(Object defaultValue,Class type){
        ClassTypeEnum classTypeEnum = ClassTypeEnum.getValue(type.getTypeName());
        try {
            if (classTypeEnum != null) {
                switch (classTypeEnum) {
                    case PACK_BOOLEAN:
                        return StringUtil.formatBoolean(defaultValue);
                    case PACK_INT:
                        return StringUtil.getIntValue(defaultValue);
                    case PACK_LONG:
                        return  StringUtil.getLongValue(defaultValue);
                    case PACK_DOUBLE:
                        return StringUtil.getDoubleValue(defaultValue);
                    case PACK_FLOAT:
                        return StringUtil.getFloatValue(defaultValue);
                    case PACK_SHORT:
                        return StringUtil.getShortValue(defaultValue);
                    case BIGDECIMAL:
                        return StringUtil.getBigDecimalValue(defaultValue);
                    default:
                        return defaultValue;
                }
            }else{
                return defaultValue;
            }
        } catch (Exception e){
            log.error("formatObject classType:{} value:{} error:",type,defaultValue,e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getLongValue("10"));
        System.out.println(getDoubleValue("0.11"));
        System.out.println(getFloatValue("0.12"));
        System.out.println(getShortValue("13"));
        System.out.println(getBigDecimalValue("14.35"));

    }
}
