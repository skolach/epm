package com.epam.rd.java.basic.practice2;

public interface List extends Container{
    void addFirst(Object element);
    void addLast(Object element);
    void removeFirst();
    void removeLast();
    Object getFirst();
    Object getLast();
    Object search(Object element);
    boolean remove(Object element);
}
