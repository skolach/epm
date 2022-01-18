package com.epam.rd.java.basic.practice6.part5;

public class Part5 {

    public static void main(String[] args) {

        Tree<Integer> tree = new Tree<>();
        tree.add(new Integer[] { 3,1,2,3,4,5,6,7,8 });
        tree.print();
        System.out.println("------------");
        tree.remove(5);
        tree.print();        
    }

}
