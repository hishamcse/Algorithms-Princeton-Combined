package com.Hisham;

import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class AssignmentProblemUsingBitmask {   // use of DP and Bitmask (O(2^n * n))

    private static int countSetBits(int n) {
        int count = 0;
        while (n > 0) {
            count += n & 1;
            n >>= 1;
        }
        return count;
    }

    public static int assign(int[][] cost) {
        int n = cost.length;
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            int x = countSetBits(mask);
            for (int j = 0; j < n; j++) {
                int y = 1 << j;
                if ((mask & y) == 0) {      // not assigned yet.so,set the task
                    dp[mask | (1 << j)] = Math.min(dp[mask | (1 << j)], dp[mask] + cost[x][j]);
                }
            }
        }
        return dp[(int) Math.pow(2, n) - 1];
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] set = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                set[i][j] = StdIn.readInt();
            }
        }
        System.out.println(assign(set));
    }
}
