package com.epam.rd.java.basic.practice5;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Part1 {

    static Logger logger = Logger.getAnonymousLogger();

    static class ThreadByClass extends Thread {
        @Override
        public void run() {
            int count = 0;
            while (count < 4) {
                System.out.println(getName());
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "msg", e);
                    interrupt();
                }
                count++;
            }
        }
    }

    public static void main(String[] args){

        Thread threadByRunable = new Thread(() -> {
            int count = 0;
            while (count < 4) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "msg", e);
                    Thread.currentThread().interrupt();
                }
                count++;
            }

        });

        threadByRunable.start();

        try {
            threadByRunable.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "msg", e);
            threadByRunable.interrupt();
        }

        Thread threadByClass = new ThreadByClass();
        threadByClass.start();
        try {
            threadByClass.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "msg", e);
            threadByClass.interrupt();
        }
    }
}