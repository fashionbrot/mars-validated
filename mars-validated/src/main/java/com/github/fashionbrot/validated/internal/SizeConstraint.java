package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.Size;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.ObjectUtil;

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
        int length= 0;
        if (value instanceof Object[]){
            Object[] array = (Object[]) value;
            length = array.length;
        }else if (value instanceof boolean[] || value instanceof Boolean[]){
            boolean[] array = (boolean[]) value;
            length = array.length;
        }else if (value instanceof byte[] || value instanceof Byte[]){
            byte[] array = (byte[]) value;
            length = array.length;
        }else if (value instanceof char[] ){
            char[] array = (char[]) value;
            length = array.length;
        }else if (value instanceof double[] || value instanceof Double[]){
            double[] array = (double[]) value;
            length = array.length;
        }else if (value instanceof float[] || value instanceof Float[]){
            float[] array = (float[]) value;
            length = array.length;
        }else if (value instanceof int[] || value instanceof Integer[]){
            int[] array = (int[]) value;
            length = array.length;
        }else if (value instanceof long[] || value instanceof Long[]){
            long[] array = (long[]) value;
            length = array.length;
        }else if (value instanceof short[] || value instanceof Short[]){
            short[] array = (short[]) value;
            length = array.length;
        }else if (value instanceof CharSequence){
            CharSequence charSequence = (CharSequence) value;
            length = charSequence.length();
        }else if (value instanceof Collection){
            Collection collection = (Collection) value;
            length = collection.size();
        }else if (value instanceof Map){
            Map map = (Map) value;
            length = map.size();
        }else{
            String str = ObjectUtil.formatString(value);
            length = str.length();
        }
        return !(min > length || length >max);
    }

}
