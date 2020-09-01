package com.Hisham;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BinaryCounter {   // Process all 2^N bit strings of length N.

    private final int n;
    private final int[] a;

    public BinaryCounter(int n) {
        this.n = n;
        a = new int[n];
        enumerate(0);
    }

    private void process() {
        for (int i = 0; i < n; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    private void enumerate(int k) {
        if (k == n) {
            process();
            return;
        }
        enumerate(k + 1);
        a[k] = 1;
        enumerate(k + 1);
        a[k] = 0;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        new BinaryCounter(n);
    }
}
