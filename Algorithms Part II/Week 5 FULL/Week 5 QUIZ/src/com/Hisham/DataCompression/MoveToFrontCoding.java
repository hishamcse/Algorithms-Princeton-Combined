package com.Hisham.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdIn;

public class MoveToFrontCoding {

    private final static int R = 256;

    public static void encode() {
        RedBlackBST<Integer, Character> bst = new RedBlackBST<>();
        for (char i = 0; i < R; i++) {
            bst.put((int) i, i);
        }
        while (!StdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int key = bst.rank((int) c);
            BinaryStdOut.write(key);
            bst.delete(key);
            bst.put(bst.min() - 1, c);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        RedBlackBST<Integer, Character> bst = new RedBlackBST<>();
        for (char i = 0; i < R; i++) {
            bst.put((int) i, i);
        }
        while (!StdIn.isEmpty()) {
            int x = BinaryStdIn.readChar();
            Integer key = bst.select(x);
            char c = bst.get(key);
            bst.delete(key);
            bst.put(bst.min() - 1, c);
        }
        BinaryStdOut.close();
    }
}
