package com.Hisham.SubStringSearch;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class KMP {     // Knuth-Morris-Pratt DFA algorithm

    private final int R;
    private final int m;
    private final int[][] dfa;

    public KMP(String pattern) {
        R = 256;
        m = pattern.length();
        dfa = new int[R][m];

        dfa[pattern.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {

            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][x];
            }

            dfa[pattern.charAt(j)][j] = j + 1;
            x = dfa[pattern.charAt(j)][x];
        }
    }

    public KMP(char[] pattern, int R) {
        this.R = R;
        this.m = pattern.length;

        // build DFA from pattern
        int m = pattern.length;
        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases.
            dfa[pattern[j]][j] = j + 1;      // Set match case.
            x = dfa[pattern[j]][x];        // Update restart state.
        }
    }

    public int search(String text) {
        int n = text.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text.charAt(i)][j];
        }
        if (j == m) {
            return i - m;
        }
        return -1;     // not found
    }

    public int search(char[] text) {

        int n = text.length;
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text[i]][j];
        }
        if (j == m) return i - m;    // found
        return n;                    // not found
    }

    public static void main(String[] args) {
        String pat = StdIn.readLine();
        String txt = StdIn.readLine();
        char[] pattern = pat.toCharArray();
        char[] text = txt.toCharArray();

        KMP kmp1 = new KMP(pat);
        int offset1 = kmp1.search(txt);

        KMP kmp2 = new KMP(pattern, 256);
        int offset2 = kmp2.search(text);

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
