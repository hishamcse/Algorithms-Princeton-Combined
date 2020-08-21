package com.Hisham.SubStringSearch;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class KMPPlus {      // Knuth-Morris-Pratt NFA algorithm

    private final String pattern;
    private final int[] next;

    public KMPPlus(String pat) {
        pattern = pat;
        int m = pat.length();
        next = new int[m];

        int j = -1;
        for (int i = 0; i < m; i++) {

            if (i == 0) {
                next[i] = -1;
            } else if (pattern.charAt(i) != pattern.charAt(j)) {
                next[i] = j;
            } else {
                next[i] = next[j];
            }

            while (j >= 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
        }

        for (int i = 0; i < m; i++)
            StdOut.println("next[" + i + "] = " + next[i]);

    }

    public int search(String text) {
        int n = text.length();
        int m = pattern.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {

            while (j >= 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
        }
        if (j == m) {
            return i - m;
        }
        return n;
    }

    public static void main(String[] args) {
        String pattern = StdIn.readLine();
        String text = StdIn.readLine();
        int m = pattern.length();
        int n = text.length();

        // substring search
        KMPPlus kmp = new KMPPlus(pattern);
        int offset = kmp.search(text);

        // print results
        StdOut.println("m = " + m + ", n = " + n);
        StdOut.println("text:    " + text);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pattern);
    }
}
