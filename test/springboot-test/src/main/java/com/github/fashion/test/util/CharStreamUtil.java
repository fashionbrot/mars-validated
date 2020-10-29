package com.github.fashion.test.util;

import java.io.IOException;
import java.nio.CharBuffer;

/**
 * @author fashionbrot
 * @version 0.1.0
 * @date 2019/12/15 23:31
 */
public class CharStreamUtil {

    private static final int BUF_SIZE = 0x800; // 2K chars (4K bytes)
    /**
     * Copies all characters between the {@link Readable} and {@link Appendable}
     * objects. Does not close or flush either object.
     *
     * @param from the object to read from
     * @param to the object to write to
     * @return the number of characters copied
     * @throws IOException if an I/O error occurs
     */
    public static long copy(Readable from, Appendable to) throws IOException {
        checkNotNull(from);
        checkNotNull(to);
        CharBuffer buf = CharBuffer.allocate(BUF_SIZE);
        long total = 0;
        while (from.read(buf) != -1) {
            buf.flip();
            to.append(buf);
            total += buf.remaining();
            buf.clear();
        }
        return total;
    }

    /**
     * Reads all characters from a {@link Readable} object into a {@link String}.
     * Does not close the {@code Readable}.
     *
     * @param r the object to read from
     * @return a string containing all the characters
     * @throws IOException if an I/O error occurs
     */
    public static String toString(Readable r) throws IOException {
        return toStringBuilder(r).toString();
    }

    /**
     * Reads all characters from a {@link Readable} object into a new
     * {@link StringBuilder} instance. Does not close the {@code Readable}.
     *
     * @param r the object to read from
     * @return a {@link StringBuilder} containing all the characters
     * @throws IOException if an I/O error occurs
     */
    private static StringBuilder toStringBuilder(Readable r) throws IOException {
        StringBuilder sb = new StringBuilder();
        copy(r, sb);
        return sb;
    }


    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

}
