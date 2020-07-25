package com.company;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {

    private final Term[] all;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("array null");
        }
        for (Term term : terms) {
            if (term == null) {
                throw new IllegalArgumentException("element null");
            }
        }
        all = new Term[terms.length];
        System.arraycopy(terms, 0, all, 0, terms.length);
        Arrays.sort(all);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("entry null");
        }
        int first = BinarySearchDeluxe.firstIndexOf(all, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        int n = numberOfMatches(prefix);
        Term[] matches = new Term[n];
        for (int i = 0; i < n; i++) {
            matches[i] = all[first++];
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches.clone();
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("entry null");
        }
        int first = BinarySearchDeluxe.firstIndexOf(all, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (first < 0) {
            return 0;
        }
        int last = BinarySearchDeluxe.lastIndexOf(all, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        return last - first + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] terms = {new Term("testing", 10), new Term("testing more", 5),
                new Term("testing still", 2), new Term("not testing", 4)};
        Autocomplete a = new Autocomplete(terms);
        StdOut.println(a.numberOfMatches("test"));
        Term[] t = a.allMatches("test");
        for (Term term : t) {
            StdOut.println(term);
        }
    }

}