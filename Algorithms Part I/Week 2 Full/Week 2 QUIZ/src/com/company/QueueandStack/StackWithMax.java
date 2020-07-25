package com.company.QueueandStack;

import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;

public class StackWithMax<Item> {
    private Node<Item> first;     // top of stack
    private int n;                // size of the stack

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public StackWithMax() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public int pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        return (int) item;                   // return the saved item
    }

    public int max(){
        int integer=(int)first.item;
        while(first!=null){
            if((int)first.item>=integer){
                integer=(int)first.item;
            }
            first=first.next;
        }
        return integer;
    }

    public static void main(String[] args) {
        StackWithMax<java.lang.Integer> stackWithMax=new StackWithMax<>();
        while (true) {
            String s = StdIn.readString();
            if (s.equals("exit")) {
                break;
            }
            stackWithMax.push(java.lang.Integer.parseInt(s));
        }
        System.out.println(stackWithMax.max());
    }
}
