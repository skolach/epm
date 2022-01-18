package com.epam.rd.java.basic.practice3;

public class Part5 {

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            System.out.println(i + " --> " + decimal2Roman(i) + " --> " +
                roman2Decimal(decimal2Roman(i)));
        }
    }

    public static String decimal2Roman(int dec) {
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLiterals = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder roman = new StringBuilder();

        for(int i=0;i<values.length;i++) {
            while(dec >= values[i]) {
                dec -= values[i];
                roman.append(romanLiterals[i]);
            }
        }
        return roman.toString();
    }

    public static int roman2Decimal(String roman) {
        if (roman.equals("")) return 0;
        if (roman.startsWith("M")) return 1000 + roman2Decimal(roman.substring(1));
        if (roman.startsWith("CM")) return 900 + roman2Decimal(roman.substring(2));
        if (roman.startsWith("D")) return 500 + roman2Decimal(roman.substring(1));
        if (roman.startsWith("CD")) return 400 + roman2Decimal(roman.substring(2));
        if (roman.startsWith("C")) return 100 + roman2Decimal(roman.substring(1));
        if (roman.startsWith("XC")) return 90 + roman2Decimal(roman.substring(2));
        if (roman.startsWith("L")) return 50 + roman2Decimal(roman.substring(1));
        if (roman.startsWith("XL")) return 40 + roman2Decimal(roman.substring(2));
        if (roman.startsWith("X")) return 10 + roman2Decimal(roman.substring(1));
        if (roman.startsWith("IX")) return 9 + roman2Decimal(roman.substring(2));
        if (roman.startsWith("V")) return 5 + roman2Decimal(roman.substring(1));
        if (roman.startsWith("IV")) return 4 + roman2Decimal(roman.substring(2));
        if (roman.startsWith("I")) return 1 + roman2Decimal(roman.substring(1));
        return 0;
    }
}