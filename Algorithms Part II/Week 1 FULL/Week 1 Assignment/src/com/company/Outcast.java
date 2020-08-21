package com.company;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet net;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        net = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max = 0;
        int sum;
        int t = -1;
        for (int j = 0; j < nouns.length; j++) {
            sum = 0;
            for (int i = 0; i < nouns.length; i++) {
                sum += net.distance(nouns[j], nouns[i]);
            }
            if (sum > max) {
                max = sum;
                t = j;
            }
        }
        return nouns[t];
    }

    // test client
    public static void main(String[] args) {
        int n=StdIn.readInt();
        WordNet wordnet = new WordNet(StdIn.readString(), StdIn.readString());
        Outcast outcast = new Outcast(wordnet);
        for (int t = 0; t < n; t++) {
            String s=StdIn.readString();
            In in = new In(s);
            String[] nouns = in.readAllStrings();
            StdOut.println(s + ": " + outcast.outcast(nouns));
        }
    }
}
