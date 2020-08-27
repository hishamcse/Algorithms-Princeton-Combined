package com.Hisham.Reduction;

import edu.princeton.cs.algs4.StdIn;

import java.util.HashSet;

public class ThreeLinearToThreeSum {

    public static boolean threeLinear(int[] A) {
        int max = 0;
        for (int i : A) {
            max = Math.max(max, Math.abs(i));
        }
        max = max + 1;
        int[] newA = new int[2 * A.length];

        for (int i = 0; i < A.length; i++) {
            newA[i] = 8 * max + A[i];
        }

        for (int i = A.length; i < newA.length; i++) {
            newA[i] = -8 * (A[i - A.length] + 2 * max);
        }
        return threeSum(newA);
    }

    public static boolean threeSum(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            HashSet<Integer> s = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                int x = -(a[i] + a[j]);
                if (s.contains(x)) {
                    return true;
                } else {
                    s.add(a[j]);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] all = new int[10];
        for (int i = 0; i < all.length; i++) {
            all[i] = StdIn.readInt();
        }
        System.out.println(threeLinear(all));
    }

}
