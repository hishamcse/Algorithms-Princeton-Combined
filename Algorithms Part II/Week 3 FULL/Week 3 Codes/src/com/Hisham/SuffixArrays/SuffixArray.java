package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class SuffixArray {

    private final Suffix[] suffixes;

    public SuffixArray(String text) {
        int n = text.length();
        suffixes = new Suffix[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = new Suffix(text, i);
        }
        Arrays.sort(suffixes);
    }

    private static class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;

        public Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }

        public int length() {
            return text.length() - index;
        }

        public char charAt(int i) {
            return text.charAt(index + i);
        }

        @Override
        public int compareTo(Suffix that) {
            if (this == that) {
                return 0;
            }
            for (int i = 0; i < Math.min(this.length(), that.length()); i++) {
                if (this.charAt(i) < that.charAt(i)) {
                    return -1;
                }
                if (this.charAt(i) > that.charAt(i)) {
                    return 1;
                }
            }
            return this.length() - that.length();
        }

        public String toString() {
            return text.substring(index);
        }
    }

    public int length() {
        return suffixes.length;
    }

    public int index(int i) {
        return suffixes[i].index;
    }

    // largest common prefix length of two side by side suffix string.
    public int lcp(int i) {
        assert i > 0;
        return lcpSuffix(suffixes[i], suffixes[i - 1]);
    }

    public static int lcpSuffix(Suffix a, Suffix b) {
        int n = Math.min(a.length(), b.length());
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return i;
            }
        }
        return n;
    }

    public String select(int i) {
        return suffixes[i].toString();
    }

    public int rank(String query) {
        int lo = 0, hi = suffixes.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(query, suffixes[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    private static int compare(String query, Suffix suffix) {
        int n = Math.min(query.length(), suffix.length());
        for (int i = 0; i < n; i++) {
            if (query.charAt(i) < suffix.charAt(i)) return -1;
            if (query.charAt(i) > suffix.charAt(i)) return +1;
        }
        return query.length() - suffix.length();
    }

    public static void main(String[] args) {
        String s = StdIn.readLine().replaceAll("\\s+", " ").trim();
        SuffixArray suffix = new SuffixArray(s);

        StdOut.println("  i ind lcp rnk select");
        StdOut.println("---------------------------");

        for (int i = 0; i < s.length(); i++) {
            int index = suffix.index(i);
            String ith = "\"" + s.substring(index, Math.min(index + 50, s.length())) + "\"";
            assert s.substring(index).equals(suffix.select(i));
            int rank = suffix.rank(s.substring(index));
            if (i == 0) {
                StdOut.printf("%3d %3d %3s %3d %s\n", i, index, "-", rank, ith);
            } else {
                int lcp = suffix.lcp(i);
                StdOut.printf("%3d %3d %3d %3d %s\n", i, index, lcp, rank, ith);
            }
        }
    }

}
