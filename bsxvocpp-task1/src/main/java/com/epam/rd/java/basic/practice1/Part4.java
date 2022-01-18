package com.epam.rd.java.basic.practice1;

public class Part4 {

    public static void main(String[] args) {
        try {

            long n1 = Long.parseLong(args[0]);
            long n2 = Long.parseLong(args[1]);            
            long divisor = 1; // the greatest divisor

            for (int i = 2; i <= (n1 < n2 ? n1 : n2); i++) {
                if (((n1 % i) == 0) && ((n2 % i) == 0))
                    divisor = i;
            }

            System.out.print(divisor);


        } catch (NumberFormatException e) {
            System.out.println("Something wrong with input parameters.");
        }
    }
}
