package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.SuffixArray;

public class CyclicRotation {

    public static boolean existence(String[] all) {
        int n = all.length;

        for (int i = 0; i < n; i++) {
            SuffixArray suffix = new SuffixArray(all[i]);
            int l = all[i].length();
            for (int j = 0; j < l; j++) {
                int m = suffix.index(j);
                String s = all[i].substring(m) + all[i].substring(0, m);
                for (int k = 0; k < n; k++) {
                    if (all[i].length() != all[k].length()) {
                        break;
                    }
                    if (i == k || s.equals(all[i])) {
                        continue;
                    }
                    if (s.equals(all[k])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        int n = Integer.parseInt(StdIn.readLine());
        String[] s = new String[n];
        for (int i = 0; i < n; i++) {
            s[i] = in.readString();
        }
        System.out.println(existence(s));
    }
}
