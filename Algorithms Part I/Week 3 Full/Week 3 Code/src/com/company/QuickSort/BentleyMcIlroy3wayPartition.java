package com.company.QuickSort;

import java.security.SecureRandom;

public class BentleyMcIlroy3wayPartition {

    private static final int CUTOFF = 8;
    private static final int MEDIAN_CUTOFF = 40;  //arbitary.as we will divide it into >5 groups.so,40/8=5;

    public static void sort(Comparable[] a, int lo, int hi) {

        int n = hi - lo + 1;

        if (n <= CUTOFF) {   //or hi<=lo+CUTOFF-1
            insertionSort(a, lo, hi);
            return;
        } else if (n <= MEDIAN_CUTOFF) {   //or hi<=lo+MEDIAN_CUTOFF-1
            int m = median(a, lo, lo + n / 2, hi);
            swap(a, m, lo);
        } else {
            int eps = n / 8;  //as,MEDIAN_CUTOFF=40
            int mid = lo + n / 2;
            int m1 = median(a, lo, lo + eps, lo + eps + eps);
            int m2 = median(a, mid - eps, mid, mid + eps);
            int m3 = median(a, hi - eps - eps, hi - eps, hi);
            int ninther = median(a, m1, m2, m3);
            swap(a, ninther, lo);
        }

        int i = lo, p = lo;
        int j = hi + 1, q = hi + 1;
        Comparable v = a[lo];

        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            if (i == j && equal(a[i], v)) {
                swap(a, ++p, i);
            }
            if (i >= j) {
                break;
            }
            swap(a, i, j);

            if (equal(a[i], v)) {
                swap(a, ++p, i);
            }
            if (equal(a[j], v)) {
                swap(a, --q, j);
            }
        }

        i = j + 1;
        for (int k = lo; k <= p; k++) {
            swap(a, k, j--);
        }
        for (int k = hi; k >= q; k--) {
            swap(a, k, i++);
        }

        sort(a, lo, j);
        sort(a, i, hi);
    }

    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--)
                swap(a, j, j - 1);
    }

    private static int median(Comparable[] a, int i, int j, int k) {
        return (less(a[i], a[j])) ?
                (less(a[j], a[k]) ? j : (less(a[i], a[k]) ? k : i)) :
                (less(a[k], a[j]) ? j : (less(a[k], a[i]) ? k : i));

    }

    public static void sort(Comparable[] a) {  //no need to shuffle in this algorithm
        int lo = 0;
        int hi = a.length - 1;
        sort(a, lo, hi);
    }

    private static boolean less(Comparable a, Comparable b) {
        if (a == b) {
            return false;
        }
        return a.compareTo(b) < 0;
    }

    private static boolean equal(Comparable a, Comparable b) {
        if (a == b) {
            return true;
        }
        return a.compareTo(b) == 0;
    }

    private static void swap(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        int n = random.nextInt(1000);
        System.out.println(n);
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = random.nextInt();
        }
//        QuickBentleyMcIlroy.sort(a);
        sort(a);
        for (int i = 0; i < n; i++) {
            System.out.println(a[i]);
        }
    }
}
