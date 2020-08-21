import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {

    private final int n;
    private final char[] str;
    private final int[] index;

    private class CircularSuffix implements Comparable<CircularSuffix> {
        int index;

        public CircularSuffix(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(CircularSuffix o) {
            for (int i = 0; i < n; i++) {
                if (str[(this.index + i) % n] < str[(o.index + i) % n]) {
                    return -1;
                }
                if (str[(this.index + i) % n] > str[(o.index + i) % n]) {
                    return 1;
                }
            }
            return 0;
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("argument is null");
        }
        n = s.length();
        str = s.toCharArray();
        index = new int[n];
        CircularSuffix[] suffixes = new CircularSuffix[n];

        for (int i = 0; i < n; i++) {
            suffixes[i] = new CircularSuffix(i);
        }
        Arrays.sort(suffixes);
        for (int i = 0; i < n; i++) {
            index[i] = suffixes[i].index;
        }
    }

    // length of s
    public int length() {
        return n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > n - 1) {
            throw new IllegalArgumentException("invalid index");
        }
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
//        CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");
        CircularSuffixArray array = new CircularSuffixArray("abcdefghijklmnopqrstuvwxyz0123456789");
        int n = array.length();
        StdOut.println(n);
        for (int i = 0; i < n; i++) {
            StdOut.println(array.index(i));
        }
    }
}
