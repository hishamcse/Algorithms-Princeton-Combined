package com.Hisham.StringSort;

import edu.princeton.cs.algs4.LSD;

public class TwoSum {

    public static boolean solve(int[] a, int t) {
        LSD.sort(a);
        int j = a.length - 1;
        int i = 0;
        while (i < j) {
            if (a[i] + a[j] == t) {
                System.out.println(i + " " + j);
                return true;
            } else if (a[i] + a[j] < t) {
                i++;
            } else if (a[i] + a[j] > t) {
                j--;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {-5, -4, -3, -5};
        if (!solve(a, -8)) {
            System.out.println("cant find any index");
        }
    }
}
