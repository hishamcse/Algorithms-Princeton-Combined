package com.Hisham.Regex;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class Clean {

    public static void main(String[] args) {
        In in   = new In(StdIn.readLine());
        while (in.hasNextLine()) {
            String s = in.readLine();
            s = s.replaceAll("\\t", "    ");
            s = s.replaceAll("\\s+$", "");
            System.out.println(s);
        }
    }
}
