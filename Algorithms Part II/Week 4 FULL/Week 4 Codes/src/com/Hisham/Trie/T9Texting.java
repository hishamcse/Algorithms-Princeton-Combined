package com.Hisham.Trie;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class T9Texting {

    public static void main(String[] args) {
        TST<Integer> dictionary = new TST<>();

        // read in dictionary of words
        In dict = new In(StdIn.readLine());
        while (!dict.isEmpty()) {
            String word = dict.readString();
            dictionary.put(word, dictionary.size());
        }
        StdOut.println("Done reading dictionary");

        In corpus = new In();
        String w = "";
        while (!corpus.isEmpty()) {
            String c = corpus.readLine();  // just use 1 char everytime
            if (!c.equals("0")) {
                w = w.concat(c);
            }
            if (c.equals("0")) {
                System.out.println("possible autocompletions " + dictionary.keysWithPrefix(w).toString());
            } else if (dictionary.keysThatMatch(w) == null) {
                System.out.println("autocomplete done " + w);
            } else {
                System.out.println(dictionary.keysThatMatch(w).toString());
            }
        }
    }
}
