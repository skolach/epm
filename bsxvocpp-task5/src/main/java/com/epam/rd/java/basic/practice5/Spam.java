package com.epam.rd.java.basic.practice5;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Spam {

    static Logger logger = Logger.getAnonymousLogger();

    private Thread[] threads;
    private String[] messages;
    private int[] delays;
    

    public Spam(final String[] messages, final int[] delays) {
        this.messages = messages;
        this.delays = delays;
    }

    public static void main(final String[] args) {

        Spam spam;

        spam = new Spam(new String[] { "aaaaaa", "bbb" }, new int[] { 333, 222 });
        spam.start();

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            if (in.nextLine().equals("")) {
                break;
            }
        }
        spam.stop();
        in.close();

    }

    public void start() {
        threads = new Thread[delays.length];
        for (int i = 0; i < delays.length; i++) {
            threads[i] = new Worker(messages[i], delays[i]);
            threads[i].setName("Worker Thread # " + i);
            threads[i].start();
        }

    }

    public void stop() {
        for (Thread thread : threads) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }

    private static class Worker extends Thread {
        private String message;
        private int delay;

        public Worker(String message, int delay) {
            this.message = message;
            this.delay = delay;
        }

        @Override
        public void run() {
            while (!interrupted()) {
                System.out.println(message);
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "msg", e);
                    interrupt();
                }
            }
        }
    }
}