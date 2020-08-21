package com.Hisham.RadixSort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Quick3WayRadixSort {

    private static int charAt(String s, int d) {
        if (d == s.length()) {
            return -1;
        }
        return s.charAt(d);
    }

    public static void sort(String[] a, int lo, int hi, int d) {

        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
            return;
        }

        int lt = lo;
        int gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;

        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) {
                swap(a, lt++, i++);
            } else if (t > v) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1, d);
        if (v >= 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                swap(a, j, j - 1);
    }

    private static boolean less(String v, String w, int d) {
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    public static void sort(String[] a) {
        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length - 1;
        sort(a, lo, hi, 0);
    }

    private static void swap(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        String[] a = in.readAllStrings();
        sort(a);
        for (String s : a) StdOut.println(s);
    }
}
