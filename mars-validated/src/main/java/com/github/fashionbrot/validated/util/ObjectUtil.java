package com.github.fashionbrot.validated.util;

import java.util.Collection;
import java.util.Map;

public class ObjectUtil {


    public static boolean isEmpty(Object[] objects){
        if (objects==null || objects.length==0){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object[] objects){
        return !isEmpty(objects);
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
}
