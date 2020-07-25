package com.company.StacksAndQueues;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;

public class DijkstraAlgorithm {
    public static void main(String[] args) {
        Stack<String> ops=new Stack<>();
        Stack<Double> vals=new Stack<>();
        while (true) {
            String s= StdIn.readString();
            if(s.equals("exit")){
                break;
            }
            switch (s) {
                case "(":
                    break;
                case "+":
                case "*":
                    ops.push(s);
                    break;
                case ")":
                    String p = ops.pop();
                    if (p.equals("+")) {
                        vals.push(vals.pop() + vals.pop());
                    } else if (p.equals("*")) {
                        vals.push(vals.pop() * vals.pop());
                    }
                    break;
                default:
                    vals.push(Double.parseDouble(s));
                    break;
            }
        }
        System.out.println(vals.pop());
    }
}
//test data [( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )]
//        (
//        1
//        +
//        (
//        (
//        2
//        +
//        3
//        )
//        *
//        (
//        4
//        *
//        5
//        )
//        )
//        )
