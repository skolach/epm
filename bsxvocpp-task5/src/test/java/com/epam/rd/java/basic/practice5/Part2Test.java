package com.epam.rd.java.basic.practice5;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Part2Test {

    @Test
    public void part2ShouldRetunZeroThreadsAfterFinish() {
        
        Part2.main(null);
        
        Thread[] tarray = new Thread[Thread.activeCount()];
        System.out.println("Threads numbers is " + Thread.enumerate(tarray));
        for (Thread thread : tarray) {
            if (thread != null) {
                System.out.println(
                    thread.getName() + " " +
                    thread.getState() + " " +
                    thread.getClass().getName());
            }
        }
        assertEquals(0, Thread.activeCount());
    }

}