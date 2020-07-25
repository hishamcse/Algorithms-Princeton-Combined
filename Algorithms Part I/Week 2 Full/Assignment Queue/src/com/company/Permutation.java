package com.company;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        Iterator<String> iterator = randomizedQueue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(iterator.next());
        }
    }
}