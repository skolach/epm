package com.epam.rd.java.basic.practice6.part5;

import java.util.Objects;

public class Tree<E extends Comparable<E>> {
    private static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private Node<E> parent;

        public Node(E value, Node<E> leftChild, Node<E> rightChild, Node<E> parent) {
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
        }

    }

    private Node<E> root = null;
    private int size = 0;

    public boolean add(E element) {

        Objects.requireNonNull(element);

        if (size == 0) {
            root = new Node<>(element, null, null, null);
            size = 1;
            return true;
        }

        return addRec(root, element);
    }

    private boolean addRec(Node<E> r, E e) {

        int compareTo = e.compareTo(r.value);

        if (compareTo == 0) {
            return false;
        }

        if (compareTo > 0) {
            if (r.rightChild != null) {
                return addRec(r.rightChild, e);
            } else {
                r.rightChild = new Node<>(e, null, null, r);
                size++;
                return true;
            }
        } else {
            if (r.leftChild != null) {
                return addRec(r.leftChild, e);
            } else {
                r.leftChild = new Node<>(e, null, null, r);
                size++;
                return true;
            }

        }
    }

    public void add(E[] elements) {
        for (E e : elements) {
            add(e);
        }
    }

    public boolean remove(E element) {
        Objects.requireNonNull(element);

        Node<E> findedNode = findByValue(root, element);

        if (findedNode == null) {
            return false;
        }

        // Delete when no children
        if ((findedNode.leftChild == null) && (findedNode.rightChild == null)) {
            if (findedNode.parent.leftChild == findedNode) {
                findedNode.parent.leftChild = null;
            }
            if (findedNode.parent.rightChild == findedNode) {
                findedNode.parent.rightChild = null;
            }

        }

        // Delete when only ONE! child
        if ((findedNode.leftChild == null && findedNode.rightChild != null)
                || (findedNode.rightChild == null && findedNode.leftChild != null)) {
            Node<E> newChildNode = findedNode.leftChild != null ? findedNode.leftChild : findedNode.rightChild;

            if (findedNode.parent.leftChild == findedNode) {
                findedNode.parent.leftChild = newChildNode;
            } else {
                findedNode.parent.rightChild = newChildNode;
            }

        }

        // Delete when have two children
        if (findedNode.leftChild != null && findedNode.rightChild != null) {

            Node<E> itrNode = findedNode.rightChild;

            while (itrNode.leftChild != null) {
                itrNode = itrNode.leftChild;
            }

            findedNode.value = itrNode.value;

            if (itrNode.rightChild != null) {
                itrNode.rightChild.parent = itrNode.parent;
                itrNode.parent.leftChild = itrNode.rightChild;
            }
        }

        size--;
        return true;
    }

    private Node<E> findByValue(Node<E> r, E e) {

        int compareTo = e.compareTo(r.value);

        if (compareTo == 0) {
            return r;
        }

        if ((compareTo > 0) && (r.rightChild != null)) {
            return findByValue(r.rightChild, e);
        }

        if ((compareTo < 0) && (r.leftChild != null)) {
            return findByValue(r.leftChild, e);
        }

        return null;
    }

    public void print() {

        traversal(root, 0);

    }

    private void traversal(Node<E> r, int level) {

        if (r.leftChild != null) {
            traversal(r.leftChild, level + 1);
        }

        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(r.value);

        if (r.rightChild != null) {
            traversal(r.rightChild, level + 1);
        }
    }

}
