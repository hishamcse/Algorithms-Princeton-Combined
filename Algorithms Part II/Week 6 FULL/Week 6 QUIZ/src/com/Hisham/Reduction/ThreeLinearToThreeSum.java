package com.Hisham.Reduction;

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
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
