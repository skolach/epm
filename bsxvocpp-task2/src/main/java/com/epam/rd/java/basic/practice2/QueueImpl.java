package com.epam.rd.java.basic.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueImpl implements Queue{

    private class Node {
        Object value;
        Node nextNode;
    }

    class IteratorImpl implements Iterator<Object> {
        private Node currentNode;
        private Node previousNode;
        private Node prevPrevNode;

        public IteratorImpl(){
            currentNode = headNode;
            previousNode = prevPrevNode = null;

        }

        @Override
        public boolean hasNext() {
            return (currentNode != null);
        }

        @Override
        public Object next() {
            if (hasNext()) {
                if (previousNode == null) {
                    Object retValue = currentNode.value;
                    previousNode = currentNode;
                    currentNode = currentNode.nextNode;
                    return retValue;
                } else {
                    prevPrevNode = previousNode;
                    previousNode = currentNode;
                    currentNode = currentNode.nextNode;
                    return previousNode.value;
                }
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove(){
            if (prevPrevNode == null) {
                headNode = currentNode;
                previousNode = prevPrevNode = null;
            } else {
                prevPrevNode.nextNode = currentNode;
            }
            size--;
        }
    }

    private Node headNode;
    private Node tailNode;
    private int size;

    public QueueImpl(){
        headNode = tailNode = null;
        size = 0;
    }

    @Override
    public void clear() {
        headNode = tailNode = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    @Override
    public void enqueue(Object element) {
        Node newNode = new Node();
        newNode.value = element;
        if (size > 0) {
            tailNode.nextNode = newNode;
            tailNode = newNode;
        } else {
            headNode = tailNode = newNode;
        }
        size++;
    }

    @Override
    public Object dequeue() {
        if (size == 0){
            return null;
        }
        Object retValue = headNode.value;
        headNode = headNode.nextNode;
        size--;
        return retValue;
    }

    @Override
    public Object top() {
        return size == 0 ? null : headNode.value;
    }

    @Override
    public String toString(){
        if (size == 0) {
            return "[]";
        }
        Iterator<Object> itr = iterator();
        StringBuilder str = new StringBuilder("[");
        int i = 0;
        while(itr.hasNext()) {
            Object nextVal = itr.next();
            str.append(nextVal == null ? "null" : nextVal.toString());
            i++;
            str.append(i == size ? "]" : ", ");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        QueueImpl q = new QueueImpl();
        for (String s : new String[]{"A", "B", null, "A", "B", "C", "B"}) {
            q.enqueue(s);
        }

        System.out.println(q);

        Iterator<Object> itr = q.iterator();
        while (itr.hasNext()) {
            Object retValue = itr.next();
            if ((retValue != null) && (retValue.equals("B"))) {
                itr.remove();
            }
        }
        System.out.println("q after removig all 'B' elements by iterator " + q);

        Iterator<Object> itr1 = q.iterator();
        while (itr1.hasNext()) {
            Object retValue = itr1.next();
            if ((retValue != null) && (retValue.equals("A"))) {
                itr1.remove();
            }
        }
        System.out.println("q after removig all 'A' elements by iterator " + q);

        System.out.println("q.top() => " + q.top());
        System.out.println("q.dequeue() => " + q.dequeue() + " The queue now is " + q);

    }
}
