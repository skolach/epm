package com.epam.rd.java.basic.practice3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part6 {

    public static void main(String[] args) {
        String input = Util.readFile("part6.txt");
        System.out.println(convert(input));
    }

    public static String convert(String input) {
        Pattern p = Pattern.compile("\\b\\p{L}{1,}", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        String input1 = input;

        // adding "_" for all words
        //in case Input = "this is a this and is", Output will be "__this __is _a __this _and __is"
        while (m.find()) {
            Matcher m1 = Pattern.compile("\\b_*" + m.group() + "\\b").matcher(input1);
            if (m1.find()){
                input1 = m1.replaceAll("_" + m1.group());
            }
        }

        // remove excactly one "_", presided with not "_", and with letter after it
        String input2 = input1;
        Matcher m2 = Pattern.compile("(?<!_)_(?=\\p{L})").matcher(input1);
        if (m2.find()) {
            input2 = m2.replaceAll("");
        }

        // replace any cout of underscoupe "_*" with only one "_"
        String input3 = input2;
        Matcher m3 = Pattern.compile("_{2,}").matcher(input2);
        if (m3.find()) {
            input3 = m3.replaceAll("_");
        }

        return input3;

    }
}
