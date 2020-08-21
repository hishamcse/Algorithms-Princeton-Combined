package com.Hisham.SubstringSearch;

import edu.princeton.cs.algs4.KMP;
import edu.princeton.cs.algs4.StdIn;

public class TandemRepeat {

    public int tandem(String pattern, String text) {
        int m = pattern.length();
        int n = text.length();
        if (m > n) {
            return 0;
        }
        int size = m;
        while (size < n) {
            KMP kmp = new KMP(pattern);
            int k = kmp.search(text);
            if (k != text.length()) {
                pattern = pattern.concat(pattern);
                size += size;
                if (size >= n) {
                    return size / (2 * m);  // already doubled so,divided by 2
                }
            } else {
                return size / (2 * m);  // already doubled so,divided by 2
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String pat = StdIn.readLine();
        String text = StdIn.readLine();
        TandemRepeat tandemRepeat = new TandemRepeat();
        int n = tandemRepeat.tandem(pat, text);
        System.out.println(n + " times");

    }
}
