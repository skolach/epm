package com.epam.rd.java.basic.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayImpl implements Array{

    class IteratorImpl  implements Iterator<Object>{
        
        private int currentPos = 0;
        private int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return currentPos != size;
        }

        @Override
        public Object next() {
            if (currentPos >= size)
                throw new NoSuchElementException();
            lastReturned = currentPos;
            return storage[currentPos++];
        }

        @Override
        public void remove(){
            if (lastReturned == -1)
                throw new NoSuchElementException("You must to do 'Next' before 'Remove'");
            ArrayImpl.this.remove(lastReturned);
            currentPos--;
            lastReturned = -1;
            size--;
        }

    }

    private int size;
    private Object[] storage;

    public ArrayImpl(int s){
        storage = new Object[s];
        size = 0;
    }

    @Override
    public void clear() {
        storage = new Object[10];
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
    public void add(Object element) {
        if (storage.length == size) {
            Object[] tmpArray = new Object[storage.length * 2];
            for (int i = 0; i < storage.length; i++) {
                tmpArray[i] = storage[i];
            }
            storage = tmpArray;
        }
        storage[size] = element;
        size++;
    }

    @Override
    public void set(int index, Object element) {
        if (index < size){
            storage[index] = element;
        }
    }

    @Override
    public Object get(int index) {
        if (index < size) {
            return storage[index];
        } else
            throw new NoSuchElementException("Out of bounds") ;
    }

    @Override
    public int indexOf(Object element) {
        int result = -1;

        if (element == null)
            return result;
        
        for (int i = 0; i < size; i++) {
            if (element.equals(storage[i])){
                result = i;
                break;
            }
        }
        
        return result;
    }

    @Override
    public void remove(int index) {
        if (index > size - 1)
            throw new NoSuchElementException();
        Object[] newArray = new Object[storage.length];
        int k = 0;
        for (int i = 0; i < storage.length; i++) {
            if (i == index)
                continue;
            newArray[k] = storage[i];
            k++;
        }
        size--;
        storage = newArray;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            str.append(storage[i] == null ? "null" : storage[i].toString());
            str.append(i==size-1?"]":", ");
        }
        return str.toString();
    }

    public static void main(String[] args) {

        ArrayImpl a = new ArrayImpl(10);
        a.add("A");
        a.add("B");        
        a.add("C");
        a.iterator().forEachRemaining(System.out::println);
        //Iterator resets its position after reach the end
        a.iterator().forEachRemaining(System.out::print);        
        System.out.println(a);
        a.remove(0);
        System.out.println(a);
        System.out.println("Index of 'B' is " + a.indexOf("B"));
        System.out.println("Element with index 1 is " + a.get(1));
        a.set(2, "CC");
        System.out.println(a);

        for (int i = 0; i < 20; i++) {
            a.add(i);
        }
        System.out.println(a);

        System.out.println("Index of 'null' is " + a.indexOf(null));
        a.remove(10);
        System.out.println("After removing index 10 = " + a);

        Iterator<Object> itr = a.iterator();

        while(itr.hasNext()) {
            Object x = itr.next();
            if (x.toString().length() > 1) {
                itr.remove();
            }
        }

        System.out.println("After removing all elements that length > 1 " + a);
    }
}
