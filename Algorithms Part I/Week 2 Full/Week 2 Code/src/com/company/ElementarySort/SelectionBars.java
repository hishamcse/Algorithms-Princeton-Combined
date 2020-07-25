package com.company.ElementarySort;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class SelectionBars {

    public static void sort(double[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++)
                if (less(a[j], a[min])) min = j;
            show(a, i, min);
            exch(a, i, min);
        }
    }

    private static void show(double[] a, int i, int min) {
        StdDraw.setYscale(-a.length + i + 0.8, i + 0.8);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for (int k = 0; k < i; k++)
            StdDraw.filledRectangle(k, a[k] * 0.3, 0.25, a[k] * 0.3);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int k = i; k < a.length; k++)
            StdDraw.filledRectangle(k, a[k] * 0.3, 0.25, a[k] * 0.3);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.filledRectangle(min, a[min] * 0.3, 0.25, a[min] * 0.3);
    }

    private static boolean less(double v, double w) {
        return v < w;
    }

    private static void exch(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        StdDraw.setCanvasSize(160, 640);
        StdDraw.setXscale(-1, n+1);
        StdDraw.setPenRadius(0.006);
        double[] a = new double[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(0.0, 1.0);
        sort(a);
    }

}
