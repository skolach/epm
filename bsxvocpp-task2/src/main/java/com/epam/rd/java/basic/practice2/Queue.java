package com.epam.rd.java.basic.practice2;

public interface Queue extends Container{
    public void enqueue(Object element);
    public Object dequeue();
    public Object top();
}
