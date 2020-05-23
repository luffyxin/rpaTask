package com.example.demo.util;

/**
 * @author Administrator
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {


    public StringUtils() {
    }
    public static boolean isEquals(String s1, String s2) {
        if (s1 == null) {
            s1 = "";
        }

        if (s2 == null) {
            s2 = "";
        }

        return s1.equals(s2);
    }
}
