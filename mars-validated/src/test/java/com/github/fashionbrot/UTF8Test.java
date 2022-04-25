package com.github.fashionbrot;

import java.io.UnsupportedEncodingException;

public class UTF8Test {


    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|([1−9]\\d5\\d2((0[1−9])|(10|11|12))(([0−2][1−9])|10|20|30|31)\\d2[0−9Xx])";
        System.out.println(new String(str.getBytes("UTF-8"),"UTF-8"));
    }
}
