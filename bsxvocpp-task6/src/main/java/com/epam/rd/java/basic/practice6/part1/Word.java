package com.epam.rd.java.basic.practice6.part1;

public class Word implements Comparable<Word> {
    private String content;
    private int frequency;

    public Word(String content) {
        this.content = content;
        this.frequency = 1;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }

        if (!(o instanceof Word)) {
            return false;
        }

        return content.equals(((Word) o).getContent());

    }

    @Override
    public int compareTo(Word o) {
        if (frequency == o.getFrequency()) {
            return o.getContent().compareTo(content);
        }
        return frequency > o.getFrequency() ? 1 : -1;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void inc() {
        frequency++;
    }
}