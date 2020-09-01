package com.Hisham;

import edu.princeton.cs.algs4.StdIn;

import java.util.LinkedList;
import java.util.List;

public class SubsetSum {    // how many subset is in the given set/list whose sum is greater than or equal to val

    // complexity O(2^n * n)
    public static int solve(List<Integer> set, int val) {
        int count = 0;
        int n = set.size();
        for (int i = 0; i < (1 << n); i++) {
            int sum = 0;
            for (int k = 0; k < n; k++) {
                int y = 1 << k;
                if ((i & y) != 0) {
                    sum += set.get(k);
                }
            }
            if (sum >= val) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            list.add(StdIn.readInt());
        }
        System.out.println(solve(list, 8));
    }
}
