package com.epam.rd.java.basic.practice3;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Part1 {

    public static final String DELIMITER = "\n";

    public static void main(String[] args) {

        String input = Util.readFile("part1.txt");

        System.out.println("============ convert1 ============");
        System.out.println(convert1(input));

        System.out.println("============ convert2 ============");
        System.out.println(convert2(input));

        System.out.println("============ convert3 ============");
        System.out.println(convert3(input));

        System.out.println("============ convert4 ============");
        System.out.println(convert4(input));
    }

    public static String removeFirstLine(String s){
        Pattern removeFirstLine = Pattern.compile("Login;Name;Email[\\r\\n]");
        return removeFirstLine.matcher(s).replaceAll("");
    }

    public static String convert1(String input) {

        Pattern p = Pattern.compile(";.*;");
        return p.matcher(removeFirstLine(input)).replaceAll(": ") + "\n";

    }

    public static String convert2(String input) {

        StringBuilder result = new StringBuilder("");

        Pattern  name = Pattern.compile(";(.*?);(.*)");
        Matcher nameMatcher = name.matcher(removeFirstLine(input));
        
        while(nameMatcher.find()) {

            result.append(nameMatcher.group(1).split(" ")[1] + " " + 
                        nameMatcher.group(1).split(" ")[0] + " (email: " +
                        nameMatcher.group(2) + ")\n");
        }

        result.setLength(result.length() - 1);
        return result.toString() + "\n";
    }

    public static String convert3(String input) {
        StringBuilder result = new StringBuilder();
        Pattern p = Pattern.compile("(.*);.*;.*@(.*)");
        Matcher m = p.matcher(input);
        while(m.find()) {
            int groupIndex = result.indexOf(m.group(2));
            if (groupIndex == -1) {
                result.append(m.group(2) + " ==>" + DELIMITER);
                result.insert( result.indexOf(DELIMITER, result.indexOf(m.group(2))),
                                " " + m.group(1) + ",");
            } else {
                result.insert( result.indexOf(DELIMITER, groupIndex), " " + m.group(1) + ",");
            }
        }
        result.setLength(result.length() - 2);
        return result.toString().replace(",\n", DELIMITER) + "\n";
    }

    static Random r = new SecureRandom();

    public static String convert4(String input) {
        StringBuilder result = new StringBuilder("Login;Name;Email;Password\n");
        Pattern p = Pattern.compile("(.*;.*;.*)");
        Matcher m = p.matcher(removeFirstLine(input));

        while(m.find()) {
            result.append(m.group(1) + ";" + 
            (r.nextInt(9999 - 1000) + 1000) + "\n");
        }

        return result.toString();
    }
}