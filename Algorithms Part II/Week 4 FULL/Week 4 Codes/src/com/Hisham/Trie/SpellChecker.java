package com.Hisham.Trie;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class SpellChecker {

    public static void main(String[] args) {
        TST<Integer> dictionary = new TST<>();

        // read in dictionary of words
        In dict = new In(StdIn.readLine());
        while (!dict.isEmpty()) {
            String word = dict.readString();
            dictionary.put(word,dictionary.size());
        }
        StdOut.println("Done reading dictionary");

        StdOut.println("Enter words, and I'll print out the misspelled ones");
        In corpus = new In();
        while (!corpus.isEmpty()) {
            String word = corpus.readString();
            if (!dictionary.contains(word)) StdOut.println(word);
        }
    }
}
