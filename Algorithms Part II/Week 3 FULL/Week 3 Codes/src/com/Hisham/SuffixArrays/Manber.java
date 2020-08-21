package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Manber {

    private final int n;
    private final String text;
    private final int[] rank;
    private final int[] index;
    private final int[] newRank;
    private int offset;

    public Manber(String s) {
        n = s.length();
        text = s;
        rank = new int[n + 1];
        index = new int[n + 1];
        newRank = new int[n + 1];

        index[n] = n;
        rank[n] = -1;

        msd();
        doit();
    }

    public int index(int i) {
        return index[i];
    }

    private void msd() {
        int r = 256;
        int[] freq = new int[r];
        for (int i = 0; i < n; i++) {
            freq[text.charAt(i)]++;
        }

        int[] cumm = new int[r];
        for (int i = 1; i < r; i++) {
            cumm[i] = cumm[i - 1] + freq[i - 1];
        }
        for (int i = 0; i < n; i++) {
            rank[i] = cumm[text.charAt(i)];
        }
        for (int i = 0; i < n; i++) {
            index[cumm[text.charAt(i)]++] = i;
        }
    }

    private void doit() {
        for (offset = 1; offset < n; offset += offset) {
            int count = 0;
            for (int i = 1; i <= n; i++) {
                if (rank[index[i]] == rank[index[i - 1]]) {
                    count++;
                } else if (count > 0) {
                    int left = i - 1 - count;
                    int right = i - 1;
                    quicksort(left, right);

                    int r = rank[index[left]];
                    for (int j = left + 1; j <= right; j++) {
                        if (less(index[j - 1], index[j])) {
                            r = rank[index[left]] + j - left;
                        }
                        newRank[index[j]] = r;
                    }
                    for (int j = left + 1; j <= right; j++) {
                        rank[index[j]] = newRank[index[j]];
                    }
                    count = 0;
                }
            }
        }
    }

    private boolean less(int v, int w) {
        return rank[v + offset] < rank[w + offset];
    }

    private void exch(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }

    private void quicksort(int lo, int hi) {
        if (hi <= lo) return;
        int i = partition(lo, hi);
        quicksort(lo, i - 1);
        quicksort(i + 1, hi);
    }

    private int partition(int lo, int hi) {
        int i = lo - 1, j = hi;
        int v = index[hi];

        while (true) {

            // find item on left to swap
            while (less(index[++i], v))
                if (i == hi) break;   // redundant

            // find item on right to swap
            while (less(v, index[--j]))
                if (j == lo) break;

            // check if pointers cross
            if (i >= j) break;

            exch(i, j);
        }

        // swap with partition element
        exch(i, hi);

        return i;
    }

    public static void main(String[] args) {
        In in=new In(StdIn.readLine());
        String s = in.readAll().replaceAll("\\s+", " ").trim();
        Manber suffix = new Manber(s);

        StdOut.println("   i  ind     select");
        StdOut.println("------------------------");

        for (int i = 0; i < s.length(); i++) {
            int index = suffix.index(i);
            String ith = "\"" + s.substring(index, Math.min(index + 50, s.length())) + "\"";
            StdOut.printf("%4d %4d  %s\n", i, index, ith);
        }
    }
}
