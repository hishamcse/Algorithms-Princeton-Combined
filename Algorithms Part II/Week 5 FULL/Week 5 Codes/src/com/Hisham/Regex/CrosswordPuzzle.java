package com.Hisham.Regex;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class CrosswordPuzzle {

    public static void main(String[] args) {
        In dictionary = new In(StdIn.readLine());
        String regex = StdIn.readLine();
        NFA nfa = new NFA(regex);
        while (!dictionary.isEmpty()) {
            String line = dictionary.readLine();
            if (nfa.recognizes(line)) {
                System.out.println(line);
            }
        }
    }
}
