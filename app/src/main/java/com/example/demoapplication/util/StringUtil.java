package com.example.demoapplication.util;

import java.util.regex.Pattern;

public class StringUtil {

    private static String pat = "[A-Z]{2}"+"-"+"[0-9]{2}"+"-"+"[A-Z]{2}"+"-"+"[0-9]{4}";

    public static boolean checkMatching(String s){
        return Pattern.matches(pat,s);
    }

}
