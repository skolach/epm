package com.epam.rd.java.basic.practice5;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Part3 {

    static Logger logger = Logger.getAnonymousLogger();

    static final Object syncro = new Object();

    private int counter = 0;
    private int counter2 = 0;

    private int numberOfThreads;
    private int numberOfIterations;

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
        this.numberOfThreads = numberOfThreads;
    }

    public static void main(final String[] args) {

        Thread mainThread = new Thread(() -> {

            Thread.currentThread().setName("main threat from Part3");
            Part3 part3 = new Part3(2, 5);
            part3.compare();
            System.out.println("------------");
            part3.compareSync();

        });

        mainThread.start();
        try {
            mainThread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "msg", e);
            mainThread.interrupt();
        }

    }

    public void compare() {
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    System.out.println(counter == counter2);
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "msg", e);
                        Thread.currentThread().interrupt();
                    }
                    counter2++;
                }
            });
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
    }

    public void compareSync() {
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                int j = 0;
                while (j < numberOfIterations) {
                    j++;
                    synchronized (syncro) {
                        System.out.println(counter == counter2);
                        counter++;
                        try {
                            Thread.sleep(100); //NOSONAR
                        } catch (InterruptedException e) {
                            logger.log(Level.SEVERE, "msg", e);
                            Thread.currentThread().interrupt();
                        }
                        counter2++;
                    }
                }
            });
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
    }
}