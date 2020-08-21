package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.SuffixArray;

public class LongestCommonSubstring {

    // first way
    public static String lcs(String s, String t) {
        SuffixArray suffixArray1 = new SuffixArray(s);
        SuffixArray suffixArray2 = new SuffixArray(t);

        String result = "";
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            int p = suffixArray1.index(i);
            int q = suffixArray2.index(j);
            String str = lcp(s, p, t, q);
            if (str.length() > result.length()) {
                result = str;
            }
            if (compare(s, p, t, q) < 0) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    private static String lcp(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i)) {
                return s.substring(p, p + i);
            }
        }
        return s.substring(p, p + n);
    }

    private static int compare(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i)) {
                return s.charAt(p + i) - t.charAt(q + i);
            }
        }
        return Integer.compare(s.length() - p, t.length() - q);

//        if (s.length() - p < t.length() - q) {
//            return -1;
//        }
//        if (s.length() - p > t.length() - q) {
//            return 1;
//        }
//        return 0;
    }

    // second way
    public static String lcsAlternative(String s, String t) {
        int n1 = s.length();

        String text = s + '\1' + t;
        int n = text.length();

        SuffixArray suffixArray = new SuffixArray(text);
        String result = "";

        for (int i = 1; i < n; i++) {

            // both from first part.so,cant be counted
            if (suffixArray.index(i) < n1 && suffixArray.index(i - 1) < n1) {
                continue;
            }
            // both from last part.so,cant be counted
            if (suffixArray.index(i) > n1 && suffixArray.index(i - 1) > n1) {
                continue;
            }

            int length = suffixArray.lcp(i);
            if (length > result.length()) {
                result = text.substring(suffixArray.index(i), suffixArray.index(i) + length);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = StdIn.readLine();
        String t = StdIn.readLine();
        System.out.println(lcs(s, t));
        System.out.println(lcsAlternative(s, t));

    }

}
