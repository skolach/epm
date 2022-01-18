package com.epam.rd.java.basic.practice1;

public class Part3 {
    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {

            System.out.print(args[i]);

            if (i < (args.length - 1))
                System.out.print(" ");
        }
    }
}
