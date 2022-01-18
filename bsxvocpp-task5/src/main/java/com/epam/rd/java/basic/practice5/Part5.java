package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Part5 {

    static Logger logger = Logger.getAnonymousLogger();
    static final String FILE_NAME = System.getProperty("user.dir") + "/part5.txt";
    static final String NEW_LINE = System.lineSeparator();
    static final long QUANTITY = 10;
    static final long LIMIT = 20;
    private static final int SLEEP_DURATION = 1;

    static final Object syncro = new Object();

    public static void main(final String[] args) {
        try {
            Files.deleteIfExists(Paths.get(FILE_NAME));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "msg", e);
        }
        File file = new File(FILE_NAME);
        Thread[] threads = new Thread[(int)QUANTITY];

        try (RandomAccessFile raFile = new RandomAccessFile(file, "rwd")) {
            raFile.setLength(QUANTITY * 20 + (QUANTITY - 1) * NEW_LINE.getBytes().length);

            for (int i = 0; i < QUANTITY; i++) {
                threads[i] = new Worker(i, String.valueOf(i), raFile);
                threads[i].start();
            }

            for (Thread thread : threads) {
                thread.join();
            }
        
        } catch (InterruptedException e){
            logger.log(Level.SEVERE, "msg", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "msg", e);
        }

        System.out.println(Part4.getInput(FILE_NAME));

    }

    static class Worker extends Thread {

        private int line;
        private String digit;
        private RandomAccessFile file;

        public Worker(int line, String digit, RandomAccessFile file) {
            this.line = line;
            this.digit = digit;
            this.file = file;
        }

        @Override
        public void run() {

            // write digits
            for (long j = 0; j < LIMIT; j++) {
                long pos = line * (LIMIT + NEW_LINE.getBytes().length) + j;
                try {
                    synchronized (syncro) {
                        file.seek(pos);
                        for (Byte b : digit.getBytes()) {
                            file.write(b);
                        }
                    }
                    getSleep(SLEEP_DURATION);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "msg", e);
                }
            }

            // write 'new line' separator
            if (line != QUANTITY) {
                try {
                    synchronized (syncro) {
                        file.seek(line * (LIMIT + NEW_LINE.getBytes().length) + LIMIT);
                        for (Byte b : NEW_LINE.getBytes()) {
                            file.write(b);
                        }
                    }
                    getSleep(SLEEP_DURATION);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "msg", e);
                }
            }
        }

        private void getSleep(int duration) {
            try {
                sleep(duration);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "msg", e);
                interrupt();
            }
        }
    }
}
