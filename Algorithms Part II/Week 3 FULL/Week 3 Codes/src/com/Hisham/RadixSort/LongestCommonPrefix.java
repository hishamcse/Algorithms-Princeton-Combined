package com.Hisham.RadixSort;

import edu.princeton.cs.algs4.StdIn;

public class LongestCommonPrefix {

    public static void main(String[] args) {
        String s = StdIn.readLine();
        String t = StdIn.readLine();
        int min = Math.min(s.length(), t.length());
        for (int i = 0; i < min; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                System.out.println(i);
                return;
            }
        }
        System.out.println(min);
    }
}
