package com.company.QueueandStack;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class DoubleSTackForQueue {
    private final Stack<String> in;
    private final Stack<String> out;

    public DoubleSTackForQueue(){
        in=new Stack<>();
        out=new Stack<>();
    }

    public boolean isEmpty(){
        return in.isEmpty() && out.isEmpty();
    }

    public int size(){
        return in.size()+out.size();
    }

    public void enqueue(String item){
        in.push(item);
    }

    public String dequeue(){
        if(isEmpty()){throw new NoSuchElementException(); }
        if(!out.isEmpty()){
            return out.pop();
        }
        while(!in.isEmpty()){
            out.push(in.pop());
        }
        return out.pop();
    }

    public static void main(String[] args) {
        DoubleSTackForQueue queue = new DoubleSTackForQueue();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }

}
