package com.company;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Term implements Comparable<Term> {

    private final String query;
    private final long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException("invalid term");
        }
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseComparator();
    }

    private static class ReverseComparator implements Comparator<Term> {

        @Override
        public int compare(Term o1, Term o2) {
            return Long.compare(o2.weight, o1.weight);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("invalid r");
        }
        return new PrefixComparator(r);
    }

    private static class PrefixComparator implements Comparator<Term> {

        private final int r;

        public PrefixComparator(int r) {
            this.r = r;
        }

        @Override
        public int compare(Term o1, Term o2) {
            int min = Math.min(o1.query.length(), o2.query.length());
            for (int i = 0; i < this.r; i++) {
                char a = o1.query.charAt(i);
                char b = o2.query.charAt(i);
                if (a != b) {
                    return Character.compare(a, b);
                }
            }
            if (this.r > min && min == o1.query.length()) {
                return -1;
            } else if (this.r > min) {  // && min==o2.query.length()
                return 1;
            } else {
                return 0;
            }
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return this.weight + "    " + this.query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term t1 = new Term("do", 23);
        Term t2 = new Term("dogcatcher", 35);
        StdOut.println(t1);
        StdOut.println(t2);
        StdOut.println(t1.compareTo(t2));
        StdOut.println(byReverseWeightOrder().compare(t1, t2));
        StdOut.println(byPrefixOrder(3).compare(t1, t2));
    }

}
