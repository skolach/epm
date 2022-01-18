package com.epam.rd.java.basic.practice1;

public class Part2 {

    public static void main(String[] args) {

        double sum = 0;
        double number = 0;

        for (String s : args) {
            if (s == null)
                continue;

            try {
                number = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                number = 0;
            }

            sum = sum + number;
        }

        System.out.print((int)sum);

    }
}
