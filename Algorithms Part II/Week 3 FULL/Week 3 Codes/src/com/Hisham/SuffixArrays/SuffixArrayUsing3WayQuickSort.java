package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SuffixArrayUsing3WayQuickSort {
    private static final int CUTOFF = 5;

    private final char[] text;
    private final int[] index;
    private final int n;

    public SuffixArrayUsing3WayQuickSort(String text) {
        n = text.length();
        text = text + '\0';
        this.text = text.toCharArray();
        this.index = new int[n];
        for (int i = 0; i < n; i++)
            index[i] = i;

        sort(0, n - 1, 0);
    }

    private void sort(int lo, int hi, int d) {

        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        char v = text[index[lo] + d];
        int i = lo + 1;
        while (i <= gt) {
            char t = text[index[i] + d];
            if (t < v) exch(lt++, i++);
            else if (t > v) exch(i, gt--);
            else i++;
        }

        sort(lo, lt - 1, d);
        if (v > 0) sort(lt, gt, d + 1);
        sort(gt + 1, hi, d);
    }

    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(index[j], index[j - 1], d); j--)
                exch(j, j - 1);
    }

    private boolean less(int i, int j, int d) {
        if (i == j) return false;
        i = i + d;
        j = j + d;
        while (i < n && j < n) {
            if (text[i] < text[j]) return true;
            if (text[i] > text[j]) return false;
            i++;
            j++;
        }
        return i > j;
    }

    private void exch(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }

    public int length() {
        return n;
    }

    public int index(int i) {
        return index[i];
    }

    public int lcp(int i) {
        return lcp(index[i], index[i - 1]);
    }

    private int lcp(int i, int j) {
        int length = 0;
        while (i < n && j < n) {
            if (text[i] != text[j]) return length;
            i++;
            j++;
            length++;
        }
        return length;
    }

    public String select(int i) {
        return new String(text, index[i], n - index[i]);
    }

    public int rank(String query) {
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(query, index[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    private int compare(String query, int i) {
        int m = query.length();
        int j = 0;
        while (i < n && j < m) {
            if (query.charAt(j) != text[i]) return query.charAt(j) - text[i];
            i++;
            j++;

        }
        if (i < n) return -1;
        if (j < m) return +1;
        return 0;
    }

    public static void main(String[] args) {
        String s = StdIn.readLine().replaceAll("\n", " ").trim();
        SuffixArrayUsing3WayQuickSort suffix1 = new SuffixArrayUsing3WayQuickSort(s);
        SuffixArray suffix2 = new SuffixArray(s);
        boolean check = true;
        for (int i = 0; check && i < s.length(); i++) {
            if (suffix1.index(i) != suffix2.index(i)) {
                StdOut.println("suffix1(" + i + ") = " + suffix1.index(i));
                StdOut.println("suffix2(" + i + ") = " + suffix2.index(i));
                String ith = "\"" + s.substring(suffix1.index(i), Math.min(suffix1.index(i) + 50, s.length())) + "\"";
                String jth = "\"" + s.substring(suffix2.index(i), Math.min(suffix2.index(i) + 50, s.length())) + "\"";
                StdOut.println(ith);
                StdOut.println(jth);
                check = false;
            }
        }

        StdOut.println("  i ind lcp rnk  select");
        StdOut.println("---------------------------");

        for (int i = 0; i < s.length(); i++) {
            int index = suffix2.index(i);
            String ith = "\"" + s.substring(index, Math.min(index + 50, s.length())) + "\"";
            int rank = suffix2.rank(s.substring(index));
            assert s.substring(index).equals(suffix2.select(i));
            if (i == 0) {
                StdOut.printf("%3d %3d %3s %3d  %s\n", i, index, "-", rank, ith);
            } else {
                // int lcp  = suffix.lcp(suffix2.index(i), suffix2.index(i-1));
                int lcp = suffix2.lcp(i);
                StdOut.printf("%3d %3d %3d %3d  %s\n", i, index, lcp, rank, ith);
            }
        }
    }
}
