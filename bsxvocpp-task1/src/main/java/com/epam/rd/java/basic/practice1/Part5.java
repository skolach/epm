package com.epam.rd.java.basic.practice1;

public class Part5 {
    public static void main(String[] args) {

        long n = Long.parseLong(args[0]);
        long sumOfDigits = 0;

        while (n >= 1) {
            sumOfDigits = sumOfDigits + n % 10;
            n = (n / 10);
        }

        System.out.print(sumOfDigits);

    }
}
