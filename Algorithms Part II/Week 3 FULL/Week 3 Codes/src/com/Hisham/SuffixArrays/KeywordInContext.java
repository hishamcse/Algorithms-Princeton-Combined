package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.SuffixArray;

public class KeywordInContext {

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        int context = Integer.parseInt(StdIn.readLine());

        String text = in.readAll().replaceAll("\\s+", " ");
        int n = text.length();

        SuffixArray suffixArray = new SuffixArray(text);
        String query = StdIn.readLine();

        for (int i = suffixArray.rank(query); i < n; i++) {
            int from1 = suffixArray.index(i);
            int to1 = Math.min(n, from1 + query.length());
            if (!query.equals(text.substring(from1, to1))) {
                break;
            }
            int from2 = Math.max(0, from1 - context);
            int to2 = Math.min(n, from1 + query.length() + context);
            System.out.println(text.substring(from2, to2));
        }
    }
}
