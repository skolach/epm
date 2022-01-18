package com.epam.rd.java.basic.practice6.part4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Range implements Iterable<Integer> {
    private final int start;
    private final int end;
    private final boolean reverse;
    private int itrPos;

    public Range(int n, int m) {
        this(n, m, false);
    }

    public Range(int firstBound, int secBound, boolean reversedOrder) {
        this.start = firstBound;
        this.end = secBound;
        this.reverse = reversedOrder;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IteratorImpl();
    }

    private final class IteratorImpl implements Iterator<Integer> {

        public IteratorImpl() {
            itrPos = reverse ? end : start;
        }

        @Override
        public boolean hasNext() {
            if (reverse) {
                return ((itrPos <= end) && (itrPos >= start));
            } else {
                return ((itrPos >= start) && (itrPos <= end));
            }
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                if (reverse) {
                    return itrPos--;
                } else {
                    return itrPos++;
                }
            }
            throw new NoSuchElementException();
        }
    }
}