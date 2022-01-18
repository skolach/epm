package com.epam.rd.java.basic.practice4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part4 {

    public static void main(String[] args) {
        SentenceParser sp = 
            new SentenceParser(Part1.getInput(System.getProperty("user.dir") + "/part4.txt"));
        Iterator<String> itr = sp.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public static class SentenceParser implements Iterable<String> {

        public static final String SENTENCE_REGEX = "[A-ZА-ЯЁЇЄҐ][\\s\\p{L},:;]+\\.";
        private String inputText;

        public SentenceParser(String input) {
            inputText = input;
        }

        public class SentenceIterator implements Iterator<String>{

            private Matcher matcher;
            private boolean hasNext;

            public SentenceIterator() {
                matcher = Pattern.compile(SENTENCE_REGEX).matcher(inputText);
            }

            @Override
            public boolean hasNext() {
                hasNext = matcher.find();
                return hasNext;
            }

            @Override
            public String next() {
                if (hasNext) {
                    return matcher.group().replaceAll("\\s", " ");
                }
                throw new NoSuchElementException();
            }
        }

        @Override
        public Iterator<String> iterator() {
            return new SentenceIterator();
        }

    }

}
