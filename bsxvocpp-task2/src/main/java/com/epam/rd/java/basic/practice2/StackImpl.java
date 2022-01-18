package com.epam.rd.java.basic.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackImpl implements Stack{

    private class Node {
        private Object value;
        private Node prev;
        private Node next;
    }

    private class IteratorImpl implements Iterator<Object> {
        Node currentNode = StackImpl.this.topNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                Object retValue = currentNode.value;
                currentNode = currentNode.next;
                return retValue;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            if (currentNode == null) {
                tailNode = tailNode.prev;
                tailNode.next = null;
            } else {
                if (currentNode.prev.prev != null) {
                    currentNode.prev.prev.next = currentNode;
                    currentNode.prev = currentNode.prev.prev;
                } else {
                    currentNode.prev = null;
                    topNode = currentNode;
                }
            }
            size--;
        }
    }

    private Node topNode;
    private Node tailNode;
    private int size;

    public StackImpl(){
        topNode = tailNode = null;
        size = 0;
    }

    @Override
    public void clear() {
        topNode = tailNode = null;
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
    public void push(Object element) {
        Node n = new Node();
        n.value = element;

        if (topNode != null) {
            n.next = topNode;
            topNode.prev = n;
        }

        if (size == 0) {
            tailNode = n;
        }

        topNode = n;
        size++;
    }

    @Override
    public Object pop() {
        if (topNode == null) {
            return null;
        }
        Object retValue = topNode.value;
        topNode = topNode.next;
        topNode.prev = null;
        size--;
        return retValue;
    }

    @Override
    public Object top() {
        return (topNode == null ? null : topNode.value);
    }

    @Override
    public String toString(){
        if (size == 0){
            return "[]";
        }
        StringBuilder sb = new StringBuilder("]");
        Iterator<Object> itr = iterator();
        int i = 0;
        while(itr.hasNext()) {
            Object retValue = itr.next();
            sb.append(retValue == null ? "llun" : retValue.toString());
            i++;
            sb.append(i == size ? "[" : " ,");
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        StackImpl stack = new StackImpl();
        for (String s : new String[]{"1", "A", "B", "C", "1", "D", "1"}) {
            stack.push(s);
        }
        System.out.println(stack);
        Iterator<Object> itr = stack.iterator();
        while(itr.hasNext()) {
            if (itr.next().toString().equals("1")) {
                itr.remove();
            }
        }
        System.out.println("Stack after removing all occurence of '1' using iterator => " + stack);
        stack.pop();
        System.out.println("stack.pop() => " + stack);
        System.out.println("stack.top() => " + stack.top());
    }
}
