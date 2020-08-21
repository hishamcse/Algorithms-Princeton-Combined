package com.Hisham.Trie;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.TrieST;

public class PrefixFree {  // interview question 1

    public static boolean isPrefixFree(String filename) {
        TrieST<Integer> dictionary = new TrieST<>();

        // read in dictionary of binary strings
        In dict = new In(filename);
        while (!dict.isEmpty()) {
            String word = dict.readString();
            dictionary.put(word, dictionary.size());
        }

        for (String s : dictionary.keys()) {
            for (String t:dictionary.keysWithPrefix(s)) {
                if(!t.equals(s) && t.startsWith(s)){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String file = StdIn.readLine();
        System.out.println(isPrefixFree(file));
    }
}
