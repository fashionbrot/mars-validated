package com.fashion.mars.validated.util;

import java.lang.reflect.Array;
import java.util.*;

public class ArrayUtils {

    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
    public static final int INDEX_NOT_FOUND = -1;

    public ArrayUtils() {
    }


    public static Map<Object, Object> toMap(Object[] array) {
        if (array == null) {
            return null;
        } else {
            Map<Object, Object> map = new HashMap((int)((double)array.length * 1.5D));

            for(int i = 0; i < array.length; ++i) {
                Object object = array[i];
                if (object instanceof Map.Entry) {
                    Map.Entry<?, ?> entry = (Map.Entry)object;
                    map.put(entry.getKey(), entry.getValue());
                } else {
                    if (!(object instanceof Object[])) {
                        throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
                    }

                    Object[] entry = (Object[])((Object[])object);
                    if (entry.length < 2) {
                        throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
                    }

                    map.put(entry[0], entry[1]);
                }
            }

            return map;
        }
    }

    public static <T> T[] toArray(T... items) {
        return items;
    }


    public static long[] clone(long[] array) {
        return array == null ? null : (long[])array.clone();
    }

    public static int[] clone(int[] array) {
        return array == null ? null : (int[])array.clone();
    }

    public static short[] clone(short[] array) {
        return array == null ? null : (short[])array.clone();
    }

    public static char[] clone(char[] array) {
        return array == null ? null : (char[])array.clone();
    }

    public static byte[] clone(byte[] array) {
        return array == null ? null : (byte[])array.clone();
    }

    public static double[] clone(double[] array) {
        return array == null ? null : (double[])array.clone();
    }

    public static float[] clone(float[] array) {
        return array == null ? null : (float[])array.clone();
    }

    public static boolean[] clone(boolean[] array) {
        return array == null ? null : (boolean[])array.clone();
    }


    public static Object[] nullToEmpty(Object[] array) {
        return isEmpty(array) ? EMPTY_OBJECT_ARRAY : array;
    }

    public static Class<?>[] nullToEmpty(Class<?>[] array) {
        return isEmpty((Object[])array) ? EMPTY_CLASS_ARRAY : array;
    }

    public static String[] nullToEmpty(String[] array) {
        return isEmpty((Object[])array) ? EMPTY_STRING_ARRAY : array;
    }

    public static long[] nullToEmpty(long[] array) {
        return isEmpty(array) ? EMPTY_LONG_ARRAY : array;
    }

    public static int[] nullToEmpty(int[] array) {
        return isEmpty(array) ? EMPTY_INT_ARRAY : array;
    }

    public static short[] nullToEmpty(short[] array) {
        return isEmpty(array) ? EMPTY_SHORT_ARRAY : array;
    }

    public static char[] nullToEmpty(char[] array) {
        return isEmpty(array) ? EMPTY_CHAR_ARRAY : array;
    }

    public static byte[] nullToEmpty(byte[] array) {
        return isEmpty(array) ? EMPTY_BYTE_ARRAY : array;
    }

    public static double[] nullToEmpty(double[] array) {
        return isEmpty(array) ? EMPTY_DOUBLE_ARRAY : array;
    }

    public static float[] nullToEmpty(float[] array) {
        return isEmpty(array) ? EMPTY_FLOAT_ARRAY : array;
    }

    public static boolean[] nullToEmpty(boolean[] array) {
        return isEmpty(array) ? EMPTY_BOOLEAN_ARRAY : array;
    }

    public static Long[] nullToEmpty(Long[] array) {
        return isEmpty((Object[])array) ? EMPTY_LONG_OBJECT_ARRAY : array;
    }

    public static Integer[] nullToEmpty(Integer[] array) {
        return isEmpty((Object[])array) ? EMPTY_INTEGER_OBJECT_ARRAY : array;
    }

    public static Short[] nullToEmpty(Short[] array) {
        return isEmpty((Object[])array) ? EMPTY_SHORT_OBJECT_ARRAY : array;
    }

    public static Character[] nullToEmpty(Character[] array) {
        return isEmpty((Object[])array) ? EMPTY_CHARACTER_OBJECT_ARRAY : array;
    }

    public static Byte[] nullToEmpty(Byte[] array) {
        return isEmpty((Object[])array) ? EMPTY_BYTE_OBJECT_ARRAY : array;
    }

    public static Double[] nullToEmpty(Double[] array) {
        return isEmpty((Object[])array) ? EMPTY_DOUBLE_OBJECT_ARRAY : array;
    }

    public static Float[] nullToEmpty(Float[] array) {
        return isEmpty((Object[])array) ? EMPTY_FLOAT_OBJECT_ARRAY : array;
    }

    public static Boolean[] nullToEmpty(Boolean[] array) {
        return isEmpty((Object[])array) ? EMPTY_BOOLEAN_OBJECT_ARRAY : array;
    }



    public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_LONG_ARRAY;
            } else {
                long[] subarray = new long[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_INT_ARRAY;
            } else {
                int[] subarray = new int[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_SHORT_ARRAY;
            } else {
                short[] subarray = new short[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_CHAR_ARRAY;
            } else {
                char[] subarray = new char[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_BYTE_ARRAY;
            } else {
                byte[] subarray = new byte[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_DOUBLE_ARRAY;
            } else {
                double[] subarray = new double[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_FLOAT_ARRAY;
            } else {
                float[] subarray = new float[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_BOOLEAN_ARRAY;
            } else {
                boolean[] subarray = new boolean[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static boolean isSameLength(Object[] array1, Object[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(long[] array1, long[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(int[] array1, int[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(short[] array1, short[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(char[] array1, char[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(byte[] array1, byte[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(double[] array1, double[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(float[] array1, float[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        return getLength(array1) == getLength(array2);
    }

    public static int getLength(Object array) {
        return array == null ? 0 : Array.getLength(array);
    }

    public static boolean isSameType(Object array1, Object array2) {
        if (array1 != null && array2 != null) {
            return array1.getClass().getName().equals(array2.getClass().getName());
        } else {
            throw new IllegalArgumentException("The Array must not be null");
        }
    }

    public static void reverse(Object[] array) {
        if (array != null) {
            reverse((Object[])array, 0, array.length);
        }
    }

    public static void reverse(long[] array) {
        if (array != null) {
            reverse((long[])array, 0, array.length);
        }
    }

    public static void reverse(int[] array) {
        if (array != null) {
            reverse((int[])array, 0, array.length);
        }
    }

    public static void reverse(short[] array) {
        if (array != null) {
            reverse((short[])array, 0, array.length);
        }
    }

    public static void reverse(char[] array) {
        if (array != null) {
            reverse((char[])array, 0, array.length);
        }
    }

    public static void reverse(byte[] array) {
        if (array != null) {
            reverse((byte[])array, 0, array.length);
        }
    }

    public static void reverse(double[] array) {
        if (array != null) {
            reverse((double[])array, 0, array.length);
        }
    }

    public static void reverse(float[] array) {
        if (array != null) {
            reverse((float[])array, 0, array.length);
        }
    }

    public static void reverse(boolean[] array) {
        if (array != null) {
            reverse((boolean[])array, 0, array.length);
        }
    }

    public static void reverse(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                boolean tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                byte tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                char tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                double tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                float tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                int tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                long tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(Object[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                Object tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                short tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void swap(Object[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((Object[])array, offset1, offset2, 1);
        }
    }

    public static void swap(long[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((long[])array, offset1, offset2, 1);
        }
    }

    public static void swap(int[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((int[])array, offset1, offset2, 1);
        }
    }

    public static void swap(short[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((short[])array, offset1, offset2, 1);
        }
    }

    public static void swap(char[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((char[])array, offset1, offset2, 1);
        }
    }

    public static void swap(byte[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((byte[])array, offset1, offset2, 1);
        }
    }

    public static void swap(double[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((double[])array, offset1, offset2, 1);
        }
    }

    public static void swap(float[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((float[])array, offset1, offset2, 1);
        }
    }

    public static void swap(boolean[] array, int offset1, int offset2) {
        if (array != null && array.length != 0) {
            swap((boolean[])array, offset1, offset2, 1);
        }
    }

    public static void swap(boolean[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                boolean aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(byte[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                byte aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(char[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                char aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(double[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                double aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(float[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                float aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(int[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                int aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(long[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                long aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(Object[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

            for(int i = 0; i < len; ++offset2) {
                Object aux = array[offset1];
                array[offset1] = array[offset2];
                array[offset2] = aux;
                ++i;
                ++offset1;
            }

        }
    }

    public static void swap(short[] array, int offset1, int offset2, int len) {
        if (array != null && array.length != 0 && offset1 < array.length && offset2 < array.length) {
            if (offset1 < 0) {
                offset1 = 0;
            }

            if (offset2 < 0) {
                offset2 = 0;
            }

            if (offset1 != offset2) {
                len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);

                for(int i = 0; i < len; ++offset2) {
                    short aux = array[offset1];
                    array[offset1] = array[offset2];
                    array[offset2] = aux;
                    ++i;
                    ++offset1;
                }

            }
        }
    }

    public static void shift(Object[] array, int offset) {
        if (array != null) {
            shift((Object[])array, 0, array.length, offset);
        }
    }

    public static void shift(long[] array, int offset) {
        if (array != null) {
            shift((long[])array, 0, array.length, offset);
        }
    }

    public static void shift(int[] array, int offset) {
        if (array != null) {
            shift((int[])array, 0, array.length, offset);
        }
    }

    public static void shift(short[] array, int offset) {
        if (array != null) {
            shift((short[])array, 0, array.length, offset);
        }
    }

    public static void shift(char[] array, int offset) {
        if (array != null) {
            shift((char[])array, 0, array.length, offset);
        }
    }

    public static void shift(byte[] array, int offset) {
        if (array != null) {
            shift((byte[])array, 0, array.length, offset);
        }
    }

    public static void shift(double[] array, int offset) {
        if (array != null) {
            shift((double[])array, 0, array.length, offset);
        }
    }

    public static void shift(float[] array, int offset) {
        if (array != null) {
            shift((float[])array, 0, array.length, offset);
        }
    }

    public static void shift(boolean[] array, int offset) {
        if (array != null) {
            shift((boolean[])array, 0, array.length, offset);
        }
    }

    public static void shift(boolean[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(byte[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(char[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(double[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(float[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(int[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(long[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(Object[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static void shift(short[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array != null) {
            if (startIndexInclusive < array.length - 1 && endIndexExclusive > 0) {
                if (startIndexInclusive < 0) {
                    startIndexInclusive = 0;
                }

                if (endIndexExclusive >= array.length) {
                    endIndexExclusive = array.length;
                }

                int n = endIndexExclusive - startIndexInclusive;
                if (n > 1) {
                    offset %= n;
                    if (offset < 0) {
                        offset += n;
                    }

                    while(n > 1 && offset > 0) {
                        int n_offset = n - offset;
                        if (offset > n_offset) {
                            swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                            n = offset;
                            offset -= n_offset;
                        } else {
                            if (offset >= n_offset) {
                                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                                break;
                            }

                            swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                            startIndexInclusive += offset;
                            n = n_offset;
                        }
                    }

                }
            }
        }
    }

    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            int i;
            if (objectToFind == null) {
                for(i = startIndex; i < array.length; ++i) {
                    if (array[i] == null) {
                        return i;
                    }
                }
            } else {
                for(i = startIndex; i < array.length; ++i) {
                    if (objectToFind.equals(array[i])) {
                        return i;
                    }
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(Object[] array, Object objectToFind) {
        return lastIndexOf(array, objectToFind, 2147483647);
    }

    public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            int i;
            if (objectToFind == null) {
                for(i = startIndex; i >= 0; --i) {
                    if (array[i] == null) {
                        return i;
                    }
                }
            } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
                for(i = startIndex; i >= 0; --i) {
                    if (objectToFind.equals(array[i])) {
                        return i;
                    }
                }
            }

            return -1;
        }
    }

    public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }

    public static int indexOf(long[] array, long valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(long[] array, long valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(long[] array, long valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(int[] array, int valueToFind) {
        return indexOf((int[])array, (int)valueToFind, 0);
    }

    public static int indexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(int[] array, int valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(int[] array, int valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(short[] array, short valueToFind) {
        return indexOf((short[])array, (short)valueToFind, 0);
    }

    public static int indexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(short[] array, short valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(short[] array, short valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(char[] array, char valueToFind) {
        return indexOf((char[])array, (char)valueToFind, 0);
    }

    public static int indexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(char[] array, char valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(char[] array, char valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(byte[] array, byte valueToFind) {
        return indexOf((byte[])array, (byte)valueToFind, 0);
    }

    public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(byte[] array, byte valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(double[] array, double valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance);
    }

    public static int indexOf(double[] array, double valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            double min = valueToFind - tolerance;
            double max = valueToFind + tolerance;

            for(int i = startIndex; i < array.length; ++i) {
                if (array[i] >= min && array[i] <= max) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(double[] array, double valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
        return lastIndexOf(array, valueToFind, 2147483647, tolerance);
    }

    public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            double min = valueToFind - tolerance;
            double max = valueToFind + tolerance;

            for(int i = startIndex; i >= 0; --i) {
                if (array[i] >= min && array[i] <= max) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(double[] array, double valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static boolean contains(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance) != -1;
    }

    public static int indexOf(float[] array, float valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(float[] array, float valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(float[] array, float valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(float[] array, float valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for(int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(boolean[] array, boolean valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for(int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static char[] toPrimitive(Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        } else {
            char[] result = new char[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static char[] toPrimitive(Character[] array, char valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        } else {
            char[] result = new char[array.length];

            for(int i = 0; i < array.length; ++i) {
                Character b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Character[] toObject(char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        } else {
            Character[] result = new Character[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static long[] toPrimitive(Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        } else {
            long[] result = new long[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static long[] toPrimitive(Long[] array, long valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        } else {
            long[] result = new long[array.length];

            for(int i = 0; i < array.length; ++i) {
                Long b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Long[] toObject(long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        } else {
            Long[] result = new Long[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static int[] toPrimitive(Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        } else {
            int[] result = new int[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static int[] toPrimitive(Integer[] array, int valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        } else {
            int[] result = new int[array.length];

            for(int i = 0; i < array.length; ++i) {
                Integer b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Integer[] toObject(int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        } else {
            Integer[] result = new Integer[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static short[] toPrimitive(Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        } else {
            short[] result = new short[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static short[] toPrimitive(Short[] array, short valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        } else {
            short[] result = new short[array.length];

            for(int i = 0; i < array.length; ++i) {
                Short b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Short[] toObject(short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        } else {
            Short[] result = new Short[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static byte[] toPrimitive(Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        } else {
            byte[] result = new byte[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static byte[] toPrimitive(Byte[] array, byte valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        } else {
            byte[] result = new byte[array.length];

            for(int i = 0; i < array.length; ++i) {
                Byte b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Byte[] toObject(byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        } else {
            Byte[] result = new Byte[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static double[] toPrimitive(Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        } else {
            double[] result = new double[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static double[] toPrimitive(Double[] array, double valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        } else {
            double[] result = new double[array.length];

            for(int i = 0; i < array.length; ++i) {
                Double b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Double[] toObject(double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        } else {
            Double[] result = new Double[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static float[] toPrimitive(Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        } else {
            float[] result = new float[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static float[] toPrimitive(Float[] array, float valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        } else {
            float[] result = new float[array.length];

            for(int i = 0; i < array.length; ++i) {
                Float b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Float[] toObject(float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        } else {
            Float[] result = new Float[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }



    public static boolean[] toPrimitive(Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        } else {
            boolean[] result = new boolean[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i];
            }

            return result;
        }
    }

    public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        } else {
            boolean[] result = new boolean[array.length];

            for(int i = 0; i < array.length; ++i) {
                Boolean b = array[i];
                result[i] = b == null ? valueForNull : b;
            }

            return result;
        }
    }

    public static Boolean[] toObject(boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        } else {
            Boolean[] result = new Boolean[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
            }

            return result;
        }
    }

    public static boolean isEmpty(Object[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(long[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(int[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(short[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(char[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(double[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(float[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return getLength(array) == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }



    public static double[] addAll(double[] array1, double... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            double[] joinedArray = new double[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }




    private static Object copyArrayGrow1(Object array, Class<?> newArrayComponentType) {
        if (array != null) {
            int arrayLength = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        } else {
            return Array.newInstance(newArrayComponentType, 1);
        }
    }



    private static Object add(Object array, int index, Object element, Class<?> clss) {
        if (array == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            } else {
                Object joinedArray = Array.newInstance(clss, 1);
                Array.set(joinedArray, 0, element);
                return joinedArray;
            }
        } else {
            int length = Array.getLength(array);
            if (index <= length && index >= 0) {
                Object result = Array.newInstance(clss, length + 1);
                System.arraycopy(array, 0, result, 0, index);
                Array.set(result, index, element);
                if (index < length) {
                    System.arraycopy(array, index, result, index + 1, length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
            }
        }
    }


    private static Object remove(Object array, int index) {
        int length = getLength(array);
        if (index >= 0 && index < length) {
            Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
            System.arraycopy(array, 0, result, 0, index);
            if (index < length - 1) {
                System.arraycopy(array, index + 1, result, index, length - index - 1);
            }

            return result;
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
    }


    static Object removeAll(Object array, int... indices) {
        int length = getLength(array);
        int diff = 0;
        int[] clonedIndices = clone(indices);
        Arrays.sort(clonedIndices);
        int end;
        int dest;
        if (isNotEmpty(clonedIndices)) {
            int i = clonedIndices.length;
            end = length;

            while(true) {
                --i;
                if (i < 0) {
                    break;
                }

                dest = clonedIndices[i];
                if (dest < 0 || dest >= length) {
                    throw new IndexOutOfBoundsException("Index: " + dest + ", Length: " + length);
                }

                if (dest < end) {
                    ++diff;
                    end = dest;
                }
            }
        }

        Object result = Array.newInstance(array.getClass().getComponentType(), length - diff);
        if (diff < length) {
            end = length;
            dest = length - diff;

            for(int i = clonedIndices.length - 1; i >= 0; --i) {
                int index = clonedIndices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }

                end = index;
            }

            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }

        return result;
    }

    static Object removeAll(Object array, BitSet indices) {
        int srcLength = getLength(array);
        int removals = indices.cardinality();
        Object result = Array.newInstance(array.getClass().getComponentType(), srcLength - removals);
        int srcIndex = 0;

        int destIndex;
        int count;
        int set;
        for(destIndex = 0; (set = indices.nextSetBit(srcIndex)) != -1; srcIndex = indices.nextClearBit(set)) {
            count = set - srcIndex;
            if (count > 0) {
                System.arraycopy(array, srcIndex, result, destIndex, count);
                destIndex += count;
            }
        }

        count = srcLength - srcIndex;
        if (count > 0) {
            System.arraycopy(array, srcIndex, result, destIndex, count);
        }

        return result;
    }

    public static <T extends Comparable<? super T>> boolean isSorted(T[] array) {
        return isSorted(array, new Comparator<T>() {
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public static <T> boolean isSorted(T[] array, Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator should not be null.");
        } else if (array != null && array.length >= 2) {
            T previous = array[0];
            int n = array.length;

            for(int i = 1; i < n; ++i) {
                T current = array[i];
                if (comparator.compare(previous, current) > 0) {
                    return false;
                }

                previous = current;
            }

            return true;
        } else {
            return true;
        }
    }






    public static boolean isSorted(double[] array) {
        if (array != null && array.length >= 2) {
            double previous = array[0];
            int n = array.length;

            for(int i = 1; i < n; ++i) {
                double current = array[i];
                if (Double.compare(previous, current) > 0) {
                    return false;
                }

                previous = current;
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isSorted(float[] array) {
        if (array != null && array.length >= 2) {
            float previous = array[0];
            int n = array.length;

            for(int i = 1; i < n; ++i) {
                float current = array[i];
                if (Float.compare(previous, current) > 0) {
                    return false;
                }

                previous = current;
            }

            return true;
        } else {
            return true;
        }
    }





    public static String[] toStringArray(Object[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_STRING_ARRAY;
        } else {
            String[] result = new String[array.length];

            for(int i = 0; i < array.length; ++i) {
                result[i] = array[i].toString();
            }

            return result;
        }
    }

    public static String[] toStringArray(Object[] array, String valueForNullElements) {
        if (null == array) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_STRING_ARRAY;
        } else {
            String[] result = new String[array.length];

            for(int i = 0; i < array.length; ++i) {
                Object object = array[i];
                result[i] = object == null ? valueForNullElements : object.toString();
            }

            return result;
        }
    }

    public static boolean[] insert(int index, boolean[] array, boolean... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                boolean[] result = new boolean[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }

    public static byte[] insert(int index, byte[] array, byte... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                byte[] result = new byte[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }

    public static char[] insert(int index, char[] array, char... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                char[] result = new char[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }

    public static double[] insert(int index, double[] array, double... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                double[] result = new double[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }

    public static float[] insert(int index, float[] array, float... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                float[] result = new float[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }

    public static int[] insert(int index, int[] array, int... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                int[] result = new int[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }

    public static long[] insert(int index, long[] array, long... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                long[] result = new long[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }

    public static short[] insert(int index, short[] array, short... values) {
        if (array == null) {
            return null;
        } else if (values != null && values.length != 0) {
            if (index >= 0 && index <= array.length) {
                short[] result = new short[array.length + values.length];
                System.arraycopy(values, 0, result, index, values.length);
                if (index > 0) {
                    System.arraycopy(array, 0, result, 0, index);
                }

                if (index < array.length) {
                    System.arraycopy(array, index, result, index + values.length, array.length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
            }
        } else {
            return clone(array);
        }
    }


    public static void shuffle(Object[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(Object[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((Object[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(boolean[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(boolean[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((boolean[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(byte[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(byte[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((byte[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(char[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(char[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((char[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(short[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(short[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((short[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(int[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(int[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((int[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(long[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(long[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((long[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(float[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(float[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((float[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static void shuffle(double[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(double[] array, Random random) {
        for(int i = array.length; i > 1; --i) {
            swap((double[])array, i - 1, random.nextInt(i), 1);
        }

    }

    public static <T> boolean isArrayIndexValid(T[] array, int index) {
        if (getLength(array) != 0 && array.length > index) {
            return index >= 0;
        } else {
            return false;
        }
    }

}
