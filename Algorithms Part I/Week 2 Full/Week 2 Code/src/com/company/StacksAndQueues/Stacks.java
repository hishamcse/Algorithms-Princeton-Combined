package com.company.StacksAndQueues;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Stacks {

    public static void main(String[] args) {

        Stack<String> strings=new Stack<>();
        while(!StdIn.isEmpty()){
            String s=StdIn.readString();
            if(s.equals("-")){
                StdOut.print(strings.pop());
            }else{
                strings.push(s);
            }
        }
    }
}
