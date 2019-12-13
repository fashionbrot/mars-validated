package com.github.fashionbrot.validated.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class IgnoreClassUtil {


    private static List<String> packageList=new ArrayList<String>();
    private static StringBuffer packagePattern=new StringBuffer() ;

    private static Pattern pattern =null;

    static {
        packageList.add("org.springframework.ui");
        packageList.add("javax.servlet");
        reload();
    }
    private static void reload(){
        for (String str:packageList) {
            if (StringUtil.isBlank(packagePattern.toString())){
                packagePattern.append(str);
            }else {
                packagePattern.append("|").append(str);
            }
        }
        pattern=Pattern.compile(packagePattern.toString());
    }

    public static void putIgnorePackage(String packageName){
        if (StringUtil.isNotBlank(packageName)) {
            packageList.add(packageName);
            reload();
        }
    }



    public static boolean checkIgnorePackage(String packageName){
        if (pattern.matcher(packageName).lookingAt()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(pattern.matcher("javax.servlet.").lookingAt());
    }

}
