package com.epam.rd.java.basic.practice3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) {
        String input = Util.readFile("part2.txt");
        System.out.println(convert(input));
    }

    public static String convert(String input) {
        Matcher m = Pattern.compile("([a-zA-Z]{1,})").matcher(input);
        StringBuilder theSmallestWords = new StringBuilder();
        StringBuilder theBiggestWords = new StringBuilder();
        int theSmallestLength = Integer.MAX_VALUE;
        int theBiggestLength = 0;
        String tmpWord;
        int tmpLength;

        while(m.find()) {
            tmpWord = m.group(1);
            tmpLength = tmpWord.length();

            if (tmpLength < theSmallestLength) {
                theSmallestWords = new StringBuilder(tmpWord);
                theSmallestLength = tmpLength;
            }
            
            if ((tmpLength == theSmallestLength) && (theSmallestWords.indexOf(tmpWord) == -1)){
                theSmallestWords.append(
                    (theSmallestWords.length() == 0 ? "" : ", ") + tmpWord
                );
            }

            if (tmpLength > theBiggestLength){
                theBiggestWords = new StringBuilder(tmpWord);
                theBiggestLength = tmpLength;
            }

            if ((tmpLength == theBiggestLength) && (theBiggestWords.indexOf(tmpWord) == -1)){
                theBiggestWords.append(
                    (theBiggestWords.length() == 0 ? "" : ", ") + tmpWord
                );
            }
        }

        return "Min: " + theSmallestWords.toString() + "\n" + "Max: " + theBiggestWords.toString();

    }
}
