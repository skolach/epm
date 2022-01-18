package com.epam.rd.java.basic.practice2;

public interface Array extends Container{
    void add(Object element);
    void set(int index, Object element);
    Object get(int index);
    int indexOf(Object element);
    void remove(int index);
}
