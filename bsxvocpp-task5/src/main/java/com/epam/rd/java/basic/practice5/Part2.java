package com.epam.rd.java.basic.practice5;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Part2 {

    static Logger logger = Logger.getAnonymousLogger();

    private static class MyInputStream extends InputStream {
        boolean firstCall = true;
        final byte[] byteSequence = "".getBytes();
        int pos = 0;

        @Override
        public int read() throws IOException {
            if (firstCall) {
                firstCall = false;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "msg", e);
                    Thread.currentThread().interrupt();
                }
            }
            return (++pos < byteSequence.length ? byteSequence[pos] & 0xFF : -1);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return read();
        }
    }

    public static void main(final String[] args) {

        InputStream oldIn = System.in;
        System.setIn(new MyInputStream());

        Spam.main(null);
        
        System.setIn(oldIn);

    }
}

