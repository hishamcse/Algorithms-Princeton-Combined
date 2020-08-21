package com.Hisham.SubstringSearch;

import edu.princeton.cs.algs4.KMP;
import edu.princeton.cs.algs4.StdIn;

public class CyclicRotation {

    public static boolean isCyclic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        String big = s + s;
        KMP kmp = new KMP(t);
        return kmp.search(big) < t.length();
    }

    public static void main(String[] args) {
        String s1 = StdIn.readLine();
        String s2 = StdIn.readLine();
        System.out.println(isCyclic(s1, s2));
    }
}
