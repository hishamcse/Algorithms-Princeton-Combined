package com.Hisham.SubStringSearch;

public class BruteForce {

    public static int search(String pattern, String text) {
        int m = pattern.length();
        int n = text.length();
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i;
            }
        }
        return -1;  // not found
    }

    public static int searchAlternate(String pattern, String text) {  // using backup
        int m = pattern.length();
        int n = text.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            } else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) {
            return i - m;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(search("His", "My name is Syed Jarullah Hisham"));
        System.out.println(searchAlternate("His", "My name is Syed Jarullah Hisham"));
    }
}
