package com.Hisham.SubStringSearch;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LongestPalindromeLinearthmic {   // linearthmic  Manacher algorithm

    // implementation from: https://github.com/yixuaz/algorithm4-princeton-cos226/blob/master/princetonSolution/src/part2/week4/substringsearch/LongestPalindromicSubStr.java

    // assumption, input str only have lower case a b c d letter
    public static String findTimeNlgN(String cur) {
        char[] cs = cur.toCharArray();
        int s = 1, e = cs.length;
        int[] ans = new int[2];
        while (s <= e) {
            int mid = s + (e - s) / 2;
            if (existsPal(mid, cs, ans)) {
                s = mid + 1;
            } else if (mid < e && existsPal(mid + 1, cs, ans)) {
                s = mid + 2;
            } else {
                e = mid - 1;
            }
        }
        return cur.substring(ans[0], ans[1]);
    }

    // las vegas
    private static boolean existsPal(int mid, char[] cs, int[] ans) {

        long ori = 0, rev = 0;
        int M = 1000000007, R = 4;

        for (int i = 0; i < mid; i++) {
            ori = (ori * R + cs[i]) % M;
            rev = (rev * R + cs[mid - 1 - i]) % M;
        }
        if (ori == rev && checkPal(cs, 0, mid - 1, ans)) {
            return true;
        }

        long base = 1, preBase = 1;
        for (int i = 1; i < mid; i++) {
            base = base * R % M;
        }

        for (int i = mid; i < cs.length; i++) {
            int delete = cs[i - mid] ;
            int add = cs[i];

            ori = (ori - (delete * base % M) + M) % M;
            ori = (ori * R + add * preBase) * R % M;

            rev = (rev - (delete * preBase % M) + M) % M;
            base = base * R % M;
            preBase = preBase * R % M;
            rev = (rev + base * add) % M;

            if (ori == rev && checkPal(cs, i - mid + 1, i, ans))
                return true;

        }
        return false;
    }

    private static boolean checkPal(char[] cs, int i, int j, int[] ans) {
        int oriI = i, oriJ = j;
        while (i < j) {
            if (cs[i++] != cs[j--]) return false;
        }
        ans[0] = oriI;
        ans[1] = oriJ + 1;
        return true;
    }

    public static void main(String[] args) {
        String s = StdIn.readLine();
        StdOut.println(LongestPalindromeLinearthmic.findTimeNlgN(s));
    }
}
