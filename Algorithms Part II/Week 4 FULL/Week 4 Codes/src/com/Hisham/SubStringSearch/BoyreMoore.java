package com.Hisham.SubStringSearch;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BoyreMoore {

    private final int R;
    private final int[] right;
    private String pattern;
    private char[] pat;

    public BoyreMoore(String pat) {
        R = 256;
        int m = pat.length();
        right = new int[R];
        pattern = pat;

        for (int i = 0; i < R; i++) {
            right[i] = -1;
        }
        for (int j = 0; j < m; j++) {
            right[pattern.charAt(j)] = j;
        }
    }

    public BoyreMoore(char[] pattern, int R) {
        this.R = R;
        right = new int[R];
        pat = new char[pattern.length];
        pat = pattern;
        int m = pattern.length;

        for (int i = 0; i < R; i++) {
            right[i] = -1;
        }
        for (int j = 0; j < m; j++) {
            right[pat[j]] = j;
        }
    }

    public int search(String text) {
        int n = text.length();
        int m = pattern.length();
        int i, skip, j;

        for (i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (j = m - 1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    skip = Math.max(1, j - right[text.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }
        return -1;     // not found
    }

    public int search(char[] text) {
        int n = text.length;
        int m = pat.length;
        int i, skip, j;

        for (i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (j = m - 1; j >= 0; j--) {
                if (pat[j] != text[i + j]) {
                    skip = Math.max(1, j - right[text[i + j]]);
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }
        return -1; // not found
    }

    public static void main(String[] args) {
        String pat = StdIn.readLine();
        String txt = StdIn.readLine();
        char[] pattern = pat.toCharArray();
        char[] text = txt.toCharArray();

        BoyreMoore boyreMoore1 = new BoyreMoore(pat);
        BoyreMoore boyreMoore2 = new BoyreMoore(pattern, 256);
        int offset1 = boyreMoore1.search(txt);
        int offset2 = boyreMoore2.search(text);

        // print results
        StdOut.println("text:    " + txt);

        StdOut.print("pattern: ");
        for (int i = 0; i < offset1; i++)
            StdOut.print(" ");
        StdOut.println(pat);

        StdOut.print("pattern: ");
        for (int i = 0; i < offset2; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }

}
