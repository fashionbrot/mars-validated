package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.Size;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.StringUtil;
import java.util.Collection;
import java.util.Map;

public class SizeConstraint implements ConstraintValidator<Size,Object> {


    @Override
    public boolean isValid(Size size, Object value, Class<?> valueType) {
        long min = size.min();
        long max = size.max();
        if ( value == null ) {
            return false;
        }

        if (value instanceof Object[]){
            Object[] array = (Object[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof boolean[]){
            boolean[] array = (boolean[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof byte[]){
            byte[] array = (byte[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof char[]){
            char[] array = (char[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof double[]){
            double[] array = (double[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof float[]){
            float[] array = (float[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof int[]){
            int[] array = (int[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof long[]){
            long[] array = (long[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof short[]){
            short[] array = (short[]) value;
            return min > array.length || array.length > max;
        }else if (value instanceof CharSequence){
            CharSequence charSequence = (CharSequence) value;
            int length = charSequence.length();
            return min > length || length > max;
        }else if (value instanceof Collection){
            Collection collection = (Collection) value;
            int length = collection.size();
            return min > length || length > max;
        }else if (value instanceof Map){
            Map map = (Map) value;
            int length = map.size();
            return min > length || length > max;
        }else{
            String str = StringUtil.formatString(value);
            int length = str.length();
            return min > length || length > max;
        }
    }

}
