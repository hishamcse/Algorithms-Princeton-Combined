package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SuffixArray;

import java.util.Arrays;

public class LongestRepeatedSubstring {

    // using SuffixArray class
    public static String lrs(String text) {
        SuffixArray suffixArray = new SuffixArray(text);  // creating suffix array and sorting it
        String lrs = "";
        for (int i = 1; i < text.length(); i++) {
            int len = suffixArray.lcp(i);
            if (len > lrs.length()) {
                lrs = text.substring(suffixArray.index(i), suffixArray.index(i) + len);
            }
        }
        return lrs;
    }

    // another way without using princeton library
    public static String lrsAnother(String text) {
        int n = text.length();
        String[] suffixes = new String[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = text.substring(i, n);
        }
        Arrays.sort(suffixes);

        String lrs = "";
        for (int i = 0; i < n - 1; i++) {
            int len = lcp(suffixes[i], suffixes[i + 1]);
            if (len > lrs.length()) {
                lrs = suffixes[i].substring(0, len);
            }
        }
        return lrs;
    }

    public static int lcp(String a, String b) {
        int n = Math.min(a.length(), b.length());
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return i;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        String text = in.readAll().replaceAll("\\s+", " ");
        StdOut.println("'" + lrs(text) + "'");
//        StdOut.println("'" + lrsAnother(text) + "'");
    }
}
