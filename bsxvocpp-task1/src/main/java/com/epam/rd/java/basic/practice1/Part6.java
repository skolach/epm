package com.epam.rd.java.basic.practice1;

public class Part6 {

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        long[] arr = new long[n];
        long currentPrimeNumber = 2;

        for (int i = 0; i < arr.length; i++) {
            while (!isPrime(currentPrimeNumber)) {
                currentPrimeNumber++;
            }
            arr[i] = currentPrimeNumber;
            currentPrimeNumber++;
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            str.append(arr[i]);
            str.append((i == arr.length - 1)? "": " ");
        }
        System.out.print(str.toString());
    }

    public static boolean isPrime(long n) {

        boolean prime = true;

        for (int i = 2; i < n; i++) {
            if ((n % i) == 0){
                prime = false;
                break;
            }
        }

        return prime;

    }
}
