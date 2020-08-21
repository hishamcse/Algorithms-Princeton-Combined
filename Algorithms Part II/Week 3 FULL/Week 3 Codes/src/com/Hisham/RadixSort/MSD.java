package com.Hisham.RadixSort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MSD {

    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_INT = 32;
    private static final int CUTOFF = 15;
    private static final int R = 256;

    private static int charAt(String s, int d) {
        if (d == s.length()) {
            return -1;
        }
        return s.charAt(d);
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    private static boolean less(String a, String b, int d) {
        for (int i = d; i < Math.min(a.length(), b.length()); i++) {
            if (a.charAt(i) < b.charAt(i)) {
                return true;
            }
            if (a.charAt(i) > b.charAt(i)) {
                return false;
            }
        }
        return a.length() < b.length();
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) {
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c + 2]++;
        }
        for (int i = 0; i < R + 1; i++) {
            count[i + 1] += count[i];
        }
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c + 1]++] = a[i];
        }
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        for (int i = 0; i < R; i++) {
            sort(a, aux, lo + count[i], lo + count[i + 1] - 1, d + 1);
        }
    }

    private static void insertion(int[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && a[j] < a[j - 1]; j--) {
                exch(a, j, j - 1);
            }
        }
    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void sort(int[] a) {
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1, 0);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi, int d) {
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }
        int shift = BITS_PER_INT - BITS_PER_BYTE * d - BITS_PER_BYTE;
        int mask = R - 1;
        int[] count = new int[R + 1];
        for (int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            count[c + 1]++;
        }
        for (int i = 0; i < R; i++) {
            count[i + 1] += count[i];
        }
        for (int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            aux[count[c]++] = a[i];
        }
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        if (d == 4) return;

        // recursively sort for each character
        if (count[0] > 0)
            sort(a, aux, lo, lo + count[0] - 1, d + 1);
        for (int i = 0; i < R; i++)
            if (count[i + 1] > count[i])
                sort(a, aux, lo + count[i], lo + count[i + 1] - 1, d + 1);
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        String[] a = in.readAllStrings();
        int n = a.length;
        sort(a);
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);

        int[] b=new int[]{4,3,6,1,3,8,9,8,7,7,7,0,-3};
        sort(b);
        for (int i = 0; i < 13; i++)
            StdOut.println(b[i]);
    }
}
