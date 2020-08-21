package com.Hisham.RadixSort;

public class KeyIndexedCounting {

    public void SortCounting(int[] a, int r) {
        int n = a.length;
        int[] count = new int[r + 1];
        int[] aux = new int[n];
        for (int i = 0; i < n; i++) {
            count[a[i] + 1]++;
        }
        for (int i = 0; i < r; i++) {
            count[i + 1] += count[i];
        }
        for (int i = 0; i < n; i++) {
            aux[count[a[i]]++] = a[i];
        }
        for (int i = 0; i < n; i++) {
            a[i] = aux[i];
        }
    }
}
