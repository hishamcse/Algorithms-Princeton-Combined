package com.Hisham.RadixSort;

public class AmericanFlagSort {

    public static void sort(int[] a, int R) {
        int[] count = new int[R];
        for (int i = 0; i < a.length; i++) {
            count[a[i]]++;
        }
        int index = 0;
        for (int i = 0; i < R; i++) {
            while (count[i]-- > 0) {
                a[index++] = i;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {4, 1, 2, 9, 4, 87, 1, 3, 2, 1, 93, 33};
        sort(a, 94);
        for (int i : a) {
            System.out.println(i);
        }
    }
}
