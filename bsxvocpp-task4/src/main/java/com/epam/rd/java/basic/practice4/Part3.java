package com.epam.rd.java.basic.practice4;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part3 {

    public static final String DOUBLE_REGEX =
        "\\d{0,}\\.\\d{0,}";
    public static final String INT_REGEX =
        "(?<=\\s)\\d{1,}(?=\\s)|\\A\\d{1,}(?=\\s)|(?<=\\s)\\d{1,}\\Z";
    public static final String CHAR_REGEX =
        "(?<=\\s)\\p{L}(?=\\s)|(?<=\\A)\\p{L}(?=\\s)|(?<=\\s)\\p{L}(?=\\Z)";
    public static final String STRING_REGEX =
        "(?<=\\s)\\p{L}{2,}(?=\\s)|(?<=\\A)\\p{L}{2,}(?=\\s)|(?<=\\s)\\p{L}{2,}(?=\\Z)";

    public static void main(String[] args) {
        String input = Part1.getInput(System.getProperty("user.dir") + "/part3.txt");

        StringBuilder doubleString = new StringBuilder();
        Matcher doubleMatcher = Pattern.compile(DOUBLE_REGEX).matcher(input);
        while(doubleMatcher.find()) {
            doubleString.append(doubleMatcher.group() + " ");
        }

        StringBuilder intString = new StringBuilder();
        Matcher intMatcher = Pattern.compile(INT_REGEX).matcher(input);
        while(intMatcher.find()) {
            intString.append(intMatcher.group() + " ");
        }

        StringBuilder charString = new StringBuilder();
        Matcher charMatcher = Pattern.compile(CHAR_REGEX).matcher(input);
        while(charMatcher.find()) {
            charString.append(charMatcher.group() + " ");
        }

        StringBuilder stringString = new StringBuilder();
        Matcher stringMatcher = Pattern.compile(STRING_REGEX).matcher(input);
        while(stringMatcher.find()) {
            stringString.append(stringMatcher.group() + " ");
        }

        Scanner in = new Scanner(System.in);
        String line;
        boolean exit = false;
        while (!exit && ((line = in.nextLine()) != null)) {
            switch (line) {
                case "stop":
                    exit = true;
                    break;
                case "double":
                    System.out.println(doubleString.toString());
                    break;
                case "int":
                    System.out.println(intString.toString());
                    break;
                case "char":
                    System.out.println(charString.toString());
                    break;
                case "String":
                    System.out.println(stringString.toString());
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }
        in.close();
    }

}
