package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Part4 {

    static final Object syncro = new Object();

    static Logger logger = Logger.getAnonymousLogger();

    static Integer maxSyncronized = Integer.MIN_VALUE;
    static Integer maxStraightforward = Integer.MIN_VALUE;

    static class MyThread extends Thread {

        private int[] vector;

        MyThread(int[] vector) {
            this.vector = vector;
        }

        @Override
        public void run() {
            int maxInVector = findMax(vector);
            synchronized (syncro) {
                maxSyncronized =
                maxSyncronized < maxInVector ? maxInVector : maxSyncronized;
            }
        }
    }

    public static String getInput(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(fileName), "cp1251");
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append(System.lineSeparator());
            }
            scanner.close();
            return sb.toString().trim();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "msg", ex);
        }
        return sb.toString();
    }

    public static int findMaxParallelized(int[][] array) {

        Thread[] threads = new MyThread[array.length];

        for (int i = 0; i < array.length; i++) {
            threads[i] = new Part4.MyThread(array[i]);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "msg", e);
                thread.interrupt();
            }
        }

        return maxSyncronized;
    }

    public static int findMaxStraightforward(int[][] array) {
        for (int[] vector : array) {
            int vectorMax = findMax(vector);
            maxStraightforward =
            maxStraightforward < vectorMax ? vectorMax : maxStraightforward;
        }
        return maxStraightforward;
    }

    public static int findMax(int[] vector) {
        int max = Integer.MIN_VALUE;
        for (int i : vector) {
            max = i > max ? i : max;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "msg", e);
                Thread.currentThread().interrupt();
            }
        }
        return max;
    }

    public static void main(final String[] args) {

        String s = getInput(System.getProperty("user.dir") + "/part4.txt");

        String[] sLines = s.split(System.lineSeparator());
        int[][] aoi = new int[sLines.length][];

        int i = 0;
        for (String row : sLines) {
            String[] numbers = row.split(" ");
            aoi[i] = new int[numbers.length];
            for (int j = 0; j < numbers.length; j++) {
                aoi[i][j] = Integer.parseInt(numbers[j]);
            }
            i++;
        }

        long startTime = System.nanoTime();

        System.out.println(findMaxParallelized(aoi));
        System.out.println(System.nanoTime() - startTime);
        startTime = System.nanoTime();
        System.out.println(findMaxStraightforward(aoi));
        System.out.println(System.nanoTime() - startTime);

    }
}
