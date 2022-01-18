package com.epam.rd.java.basic.practice6.part1;

import java.util.AbstractList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordContainer extends AbstractList<Word> {

    private static final String STOP = "stop";
    private Word[] data;
    private int size;

    public WordContainer() {
        data = new Word[10];
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

    private void checkIndex(int index){
        if (index < 0 || index > size - 1){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Word set(int index, Word element) {
        checkIndex(index);
        Word oldValue = data[index];
        data[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, Word element) {
        if (index > data.length - 1) {
            Word[] tmpData = new Word[data.length * 2];
            System.arraycopy(data, 0, tmpData, 0, data.length);
            data = tmpData;
        }
        data[index] = element;
        size++;
    }

    @Override
    public Word get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {

        Pattern p = Pattern.compile(
            "(\\A\\w{1,}(?=\\s))|(?<=\\s)\\w{1,}(?=\\s)|((?<=\\s)\\w{1,}\\Z)");

        WordContainer wc = new WordContainer();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            Matcher m = p.matcher(in.nextLine());

            while (m.find()) {
                if (STOP.equals(m.group())) {
                    break;
                }
                Word w = new Word(m.group());
                int index = wc.indexOf(w);
                if (index > -1) {
                    wc.get(index).inc();
                } else {
                    wc.add(w);
                }
            }
        }
        
        //Sonar requirements
        in.close();

        wc.sort(Word::compareTo);

        for (int i = wc.size() - 1; i > -1; i--) {
            System.out.println(
                wc.get(i).getContent() + " : " + wc.get(i).getFrequency());
        }
    }
}