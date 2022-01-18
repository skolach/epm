package com.epam.rd.java.basic.practice3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part3 {

    public static void main(String[] args) {
        String input = Util.readFile("part3.txt");
        System.out.println(convert(input));
    }

    public static String convert(String input) {

        StringBuffer sb = new StringBuffer();
        String replace;
        Matcher m = Pattern.compile("(\\p{L}{1})(\\p{L}{2,})").matcher(input);
        while (m.find()) {
            replace = 
            (Character.isUpperCase(m.group(1).charAt(0)) ?
                Character.toLowerCase(m.group(1).charAt(0)) :
                Character.toUpperCase(m.group(1).charAt(0))) +
            m.group(2);
            m.appendReplacement(sb, replace);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
