package com.epam.rd.java.basic.practice1;

public class Part7 {
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final short BASE = 26;
    public static final String ARROW = " ==> ";


    public static void main(String[] args) {
        System.out.println("A ==> " + str2int("A") + ARROW + int2str(str2int("A")));        
        System.out.println("B ==> " + str2int("B") + ARROW + int2str(str2int("B")));
        System.out.println("Z ==> " + str2int("Z") + ARROW + int2str(str2int("Z")));
        System.out.println("AA ==> " + str2int("AA") + ARROW + int2str(str2int("AA")));
        System.out.println("AZ ==> " + str2int("AZ") + ARROW + int2str(str2int("AZ")));
        System.out.println("BA ==> " + str2int("BA") + ARROW + int2str(str2int("BA")));
        System.out.println("ZZ ==> " + str2int("ZZ") + ARROW + int2str(str2int("ZZ")));
        System.out.println("AAA ==> " + str2int("AAA") + ARROW + int2str(str2int("AAA")));
    }

    public static int str2int(String number) {

        double result = 0;

        for (int i = 0; i < number.length(); i++) {

            result = result + Math.pow(BASE, (double)number.length() - i - 1)
                * (ALPHABET.indexOf(number.charAt(i)) + 1);

        }

        return (int)result;

    }

    public static String int2str(int number) {

        int digit = 0;
        StringBuilder tmpStr = new StringBuilder();
        StringBuilder resultStr = new StringBuilder();

        while (number > BASE) {

            digit = number % BASE;

            digit = digit == 0 ? BASE : digit;

            tmpStr.append(ALPHABET.charAt(digit - 1));
            number = (number - digit)/BASE;

        }

        tmpStr.append(ALPHABET.charAt(number - 1));

        for (int i = 0; i < tmpStr.length(); i++) {
            resultStr.append(tmpStr.charAt(tmpStr.length() - i -1));
        }

        return resultStr.toString();
    }

    public static String rightColumn(String number) {

        char[] result = new char[number.length() + 1];
        boolean overload = true;

        for (int i = (number.length() - 1); i > (-1 -1); i--){
            if (overload) {

                if (i == -1) {
                    result[i + 1] = ALPHABET.charAt(0);
                    break;
                }

                if (number.charAt(i) == ALPHABET.charAt(BASE - 1)) {
                    result[i + 1] = ALPHABET.charAt(0);
                } else {
                    result[i + 1] = ALPHABET.charAt(ALPHABET.indexOf(number.charAt(i)) + 1);
                    overload = false;
                }

            } else {
                if (i != -1)
                    result[i + 1] = number.charAt(i);
            }
        }

        return (new String(result)).trim();

    }

}
