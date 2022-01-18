package com.epam.rd.java.basic.practice6.part3;

public class Parking {
    private boolean[] places;

    public Parking(int capacity) {
        places = new boolean[capacity];
    }

    public boolean arrive(int k) {
        checkBounds(k);
        if (!places[k]) {
            places[k] = true;
            return true;
        }
        for (int i = k + 1; i < places.length; i++) {
            if (!places[i]) {
                places[i] = true;
                return true;
            }
        }
        for (int i = 0; i < k; i++) {
            if (!places[i]) {
                places[i] = true;
                return true;
            }
        }
        return false;
    }

    public boolean depart(int k) {
        checkBounds(k);
        boolean tmpVal = places[k];
        places[k] = false;
        return tmpVal;
    }

    public void print() {
        System.out.println(this);
    }

    private void checkBounds(int index) throws IllegalArgumentException {
        if ((index < 0) || (index > places.length - 1)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (boolean b : places) {
            sb.append(b ? 1 : 0);
        }
        return sb.toString();
    }
}