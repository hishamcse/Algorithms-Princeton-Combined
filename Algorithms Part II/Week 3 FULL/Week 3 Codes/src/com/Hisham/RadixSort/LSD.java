package com.Hisham.RadixSort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LSD {

    // for string
    public static void sort(String[] a, int w) {
        int r = 256;
        int n = a.length;
        String[] aux = new String[n];

        for (int d = w - 1; d >= 0; d--) {

            int[] count = new int[r + 1];
            for (int i = 0; i < n; i++) {
                count[a[i].charAt(d) + 1]++;
            }
            for (int i = 0; i < r; i++) {
                count[i + 1] += count[i];
            }
            for (int i = 0; i < n; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
            }
        }
    }

    // for integer
    public static void sort(int[] a) {
        int BITS_PER_BYTE = 8;
        int BITS = 256;
        int r = 1 << BITS_PER_BYTE;
        int MASK = r - 1;
        int w = BITS / BITS_PER_BYTE;

        int n = a.length;
        int[] aux = new int[n];

        for (int d = 0; d < w; d++) {
            int[] count = new int[r + 1];

            for (int i = 0; i < n; i++) {
                int c = (a[i] >> BITS_PER_BYTE * d) & MASK;
                count[c + 1]++;
            }
            for (int i = 0; i < n; i++) {
                count[i + 1] += count[i];
            }
            // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
//            if (d == w - 1) {
//                int shift1 = count[r] - count[r / 2];
//                int shift2 = count[r / 2];
//                for (int i = 0; i < r / 2; i++) {
//                    count[i] += shift1;
//                }
//                for (int i = r / 2; i < r; i++) {
//                    count[i] -= shift2;
//                }
//            }
            for (int i = 0; i < n; i++) {
                int c = (a[i] >> BITS_PER_BYTE * d) & MASK;
                aux[count[c]++] = a[i];
            }
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        String[] a = in.readAllStrings();
        int n = a.length;

        // check that strings have fixed length
        int w = a[0].length();
        for (int i = 0; i < n; i++)
            assert a[i].length() == w : "Strings must have fixed length";

        // sort the strings
        sort(a, w);

        // print results
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);

        int[] b=new int[]{4,3,6,1,3,8,9,8,7,7,7,0,3};
        sort(b);
        for (int i = 0; i < 13; i++)
            StdOut.println(b[i]);
    }
}
