package com.Hisham.Regex;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class GREP {

    public static void main(String[] args) {
        String s = StdIn.readLine();
        String regex = "(.*" + s + ".*)";
        NFA nfa = new NFA(regex);
        In in = new In(StdIn.readLine());
        while (!in.isEmpty()) {
            String line = in.readLine();
            if (nfa.recognizes(line)) {
                System.out.println(line);
            }
        }
    }
}
