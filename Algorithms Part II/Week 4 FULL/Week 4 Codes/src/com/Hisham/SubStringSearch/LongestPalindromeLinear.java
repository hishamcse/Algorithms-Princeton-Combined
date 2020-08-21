package com.Hisham.SubStringSearch;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LongestPalindromeLinear {   // linear Manacher algorithm( from booksite )

    private final int[] p;    // p[i] = length of longest palindromic substring of t, centered at i
    private final String s;   // original string
    private char[] t;         // transformed string

    public LongestPalindromeLinear(String s) {
        this.s = s;
        preprocess();
        p = new int[t.length];

        int center = 0, right = 0;
        for (int i = 1; i < t.length - 1; i++) {
            int mirror = 2 * center - i;

            if (right > i) {
                p[i] = Math.min(right - i, p[mirror]);
            }
            while (t[i + (1 + p[i])] == t[i - (1 + p[i])]) {
                p[i]++;
            }

            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }
    }

    private void preprocess() {
        t = new char[s.length() * 2 + 3];
        t[0] = '$';
        t[2 * s.length() + 2] = '@';
        for (int i = 0; i < s.length(); i++) {
            t[2 * i + 1] = '#';
            t[2 * i + 2] = s.charAt(i);
        }
        t[2 * s.length() + 1] = '#';
    }

    public String longestPalindromicSubstring() {
        int length = 0;
        int center = 0;
        for (int i = 1; i < t.length - 1; i++) {
            if (p[i] > length) {
                length = p[i];
                center = i;
            }
        }
        return s.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
    }

    // longest palindromic substring centered at index i/2
    public String longestPalindromicSubstring(int i) {
        int length = p[i + 2];
        int center = i + 2;
        return s.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
    }

    public static void main(String[] args) {
        String s = StdIn.readLine();
        LongestPalindromeLinear manacher = new LongestPalindromeLinear(s);
        StdOut.println(manacher.longestPalindromicSubstring());
        for (int i = 0; i < 2 * s.length(); i++)
            StdOut.println(i + ": " + manacher.longestPalindromicSubstring(i));
    }

}
