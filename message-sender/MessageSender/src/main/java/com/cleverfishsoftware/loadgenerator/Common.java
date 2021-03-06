/*
 */
package com.cleverfishsoftware.loadgenerator;

/**
 *
 * @author peter
 */
public class Common {

    public static final int ONE_KB = 1024;
    public static final int ONE_MB = 1000 * ONE_KB;

    public static boolean NotNullOrEmpty(String value) {
        return value != null && value.length() > 0;
    }

    public static boolean NullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isTrue(String value) {
        return NotNullOrEmpty(value) && (value.toLowerCase().equals("y")
                || value.toLowerCase().equals("yes")
                || value.toLowerCase().equals("true"));
    }

}
