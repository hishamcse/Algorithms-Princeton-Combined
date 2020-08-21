package com.Hisham.Regex;

import edu.princeton.cs.algs4.StdIn;

public class Validate {

    public static void main(String[] args) {
        String regex = StdIn.readLine();
        String text = StdIn.readLine();
        System.out.println(text.matches(regex));
    }
}
