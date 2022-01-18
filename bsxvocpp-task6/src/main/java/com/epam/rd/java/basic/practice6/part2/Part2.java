package com.epam.rd.java.basic.practice6.part2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Part2 {

    private static final int K = 4;

    private static void fillList(List<Integer> list) {
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {

        ArrayList<Integer> arrayListForIndexedRemove = new ArrayList<>();
        fillList(arrayListForIndexedRemove);
        System.out.println("ArrayList#Index: " +
            removeByIndex(arrayListForIndexedRemove, K) + " ms");

        LinkedList<Integer> linkedListForIndexedRemove = new LinkedList<>();
        fillList(linkedListForIndexedRemove);
        System.out.println("LinkedList#Index: " +
            removeByIndex(linkedListForIndexedRemove, K) + " ms");

        ArrayList<Integer> arrayListForIteratorRemove = new ArrayList<>();
        fillList(arrayListForIteratorRemove);
        System.out.println("ArrayList#Iterator: " +
            removeByIterator(arrayListForIteratorRemove, K) + " ms");

        LinkedList<Integer> linkedListForIteratorRemove = new LinkedList<>();
        fillList(linkedListForIteratorRemove);
        System.out.println("LinkedList#Iterator: " +
            removeByIterator(linkedListForIteratorRemove, K) + " ms");

    }

    public static long removeByIndex(final List<Integer> list, final int k) {

        long start = System.nanoTime();
        int j = 1;

        while (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (j == k) {
                    list.remove(i);
                    j = 1 + 1;
                } else {
                    j++;
                }
            }
        }
        return (System.nanoTime() - start) / 10_000;
    }

    public static long removeByIterator(final List<Integer> list, int k) {

        long start = System.nanoTime();
        int j = 1;

        while (list.size() > 1) {
            Iterator<Integer> itr = list.listIterator();
            while (itr.hasNext()) {
                itr.next();
                if (j == k) {
                    itr.remove();
                    j = 1;
                } else {
                    j++;
                }
            }
        }

        return (System.nanoTime() - start) / 10_000;
    }
}