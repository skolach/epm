package com.epam.rd.java.basic.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListImpl implements List{

    class Node {
        private Object value;
        private Node nextNode;
    }

    class IteratorImpl implements Iterator<Object> {
        private Node currentNode;
        private Node previousNode;
        private Node prevPrevNode;

        public IteratorImpl(){
            currentNode = firstNode;
            previousNode = null;

        }

        @Override
        public boolean hasNext() {
            return (currentNode != null);
        }

        @Override
        public Object next() {
            if (hasNext()) {
                Object returnValue = currentNode.value;
                prevPrevNode = previousNode;
                previousNode = currentNode;
                currentNode = currentNode.nextNode;
                return returnValue;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove(){
            //removing first node
            if (prevPrevNode == null) {
                ListImpl.this.removeFirst();
            } else{
                //removing last node
                if (currentNode == null) {
                    ListImpl.this.removeLast();
                } else {
                    //removing node in the middle
                    prevPrevNode.nextNode = currentNode;
                    size--;
                }
            }
        }
    }

    private Node firstNode;
    private Node lastNode;
    private int size;

    public ListImpl(){
        firstNode = lastNode = null;
        size = 0;
    }

    @Override
    public void clear() {
        firstNode = lastNode = null;
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
    public void addFirst(Object element) {
        Node n = new Node();
        n.value = element;
        n.nextNode = firstNode;
        firstNode = n;
        size++;
    }

    @Override
    public void addLast(Object element) {
        Node n = new Node();
        n.value = element;

        if (lastNode != null) {
            lastNode.nextNode = n;
        }

        lastNode = n;
        if (size == 0) {
            firstNode = lastNode;
        }
        size++;
    }

    @Override
    public void removeFirst() {
        if (firstNode != null) {
            firstNode = firstNode.nextNode;
            size--;
        }
    }

    @Override
    public void removeLast() {
        Node currentN = firstNode;
        Node previousNode = null;

        while (currentN.nextNode != null) {
            previousNode = currentN;
            currentN = currentN.nextNode;
        }

        if (previousNode != null){
            previousNode.nextNode = null;
        }

        size--;
    }

    @Override
    public Object getFirst() {
        if (firstNode != null) {
            return firstNode.value;
        }
        return null;
    }

    @Override
    public Object getLast() {
        if (size == 0){
            return null;
        }
        Node currentNode = firstNode;
        while(currentNode.nextNode != null) {
            currentNode = currentNode.nextNode;
        }
        return currentNode.value;
    }

    @Override
    public Object search(Object element) {
        if ((size == 0) || (element == null)) {
            return null;
        }
        Node currentNode = firstNode;
        while (currentNode != null) {
            if (element.equals(currentNode.value)) {
                return currentNode.value;
            } else {
                currentNode = currentNode.nextNode;
            }
        }
        return null;
    }

    @Override
    public boolean remove(Object element) {

        if (size == 0){
            return false;
        }
        Node previousNode = null;
        Node currentNode = firstNode;
        boolean hasNext = true;

        while (hasNext){
            if (((currentNode.value != null) && (currentNode.value.equals(element))) ||
                ((currentNode.value == null) && (element == null))) {
                
                if (previousNode == null) {
                    this.firstNode = currentNode.nextNode;
                    size--;
                    return true;
                }

                if (currentNode.nextNode == null) {
                    previousNode.nextNode = null;
                    size--;
                    return true;
                }

                previousNode.nextNode = currentNode.nextNode;
                size--;
                return true;
            }

            hasNext = currentNode.nextNode != null;

            if (hasNext) {
                previousNode = currentNode;
                currentNode = currentNode.nextNode;
            }
        }
        return false;
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
            Object nextValue = itr.next();
            str.append(nextValue == null ? "null" : nextValue.toString());
            i++;
            str.append(i == size ? "]" : ", ");
        }
        return str.toString();
    }


    public static void main(String[] args) {

        ListImpl l = new ListImpl();
        l.addLast("A");
        l.addLast("B");
        l.addLast("C");
        System.out.println("expected [A,B,C] => " + l);
        l.addFirst("AA");
        System.out.println("l.addFirst('AA') => " + l);
        l.remove("B");
        System.out.println("l.remove('B') => " + l);
        System.out.println("l.search('B') => " + l.search("B"));
        System.out.println("l.search('AA') => " + l.search("AA"));
        System.out.println("l.getFirst() + l.getLast() => " + l.getFirst() + " " + l.getLast());
        System.out.println("l.size() => " + l.size());
        for (String s : new String[]{"A", "B", null, "C", "D", "E", "B", "B"}) {
            l.addLast(s);
        }
        l.addFirst("B");

        System.out.println(l);

        Iterator<Object> itr = l.iterator();
        while(itr.hasNext()) {
            Object nextVal = itr.next();
            if ((nextVal != null) && (nextVal.equals("B"))) {
                itr.remove();
            }
        }
        System.out.println("After removing all 'B' occurence => " + l);
        System.out.println("Searching non existing element '1' => " + l.search("1"));
        System.out.println("Searching existing element 'D' => " + l.search("D"));
        l.remove("E");
        System.out.println("After removing 'E' => " + l);

        l.remove(null);
        System.out.println("After removing 'null' => " + l);

        ListImpl ll = new ListImpl();
        System.out.println("Executing remove om empty list =>" + ll.remove("1"));

        System.out.println("Searching on empty list => " + (new ListImpl()).search("1"));

    }
}
