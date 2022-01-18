package com.epam.rd.java.basic.practice4;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part6 {

    public static void main(String[] args) {
        String input = Part1.getInput("part6.txt");
        Matcher matcherCyrilic = Pattern.compile("[а-яА-ЯіїІЇЁёєЄ]+").matcher(input);
        Matcher matcherLatin = Pattern.compile("[a-zA-Z]+").matcher(input);

        StringBuilder stringCyrilic = new StringBuilder();
        while (matcherCyrilic.find()) {
            stringCyrilic.append(matcherCyrilic.group() + " ");
        }

        StringBuilder stringLatin = new StringBuilder();
        while (matcherLatin.find()) {
            stringLatin.append(matcherLatin.group() + " ");
        }

        String line;
        Scanner in = new Scanner(System.in);
        while (((line = in.nextLine()) != null) && !line.equals("stop")) {

            switch (line) {
                case "Latn":
                    System.out.println(line + ": " + stringLatin.toString());
                    break;
                case "Cyrl":
                    System.out.println(line + ": " + stringCyrilic.toString());
                    break;                    
                default:
                    System.out.println(line + ": " + "Incorrect input");
            }

        }
        in.close();
    }

}
