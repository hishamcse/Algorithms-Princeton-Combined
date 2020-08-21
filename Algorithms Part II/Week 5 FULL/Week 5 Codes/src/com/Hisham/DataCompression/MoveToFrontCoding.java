package com.Hisham.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdIn;

public class MoveToFrontCoding {

    private final static int R = 256;

    public static void encode() {
        RedBlackBST<Integer, Character> bst = new RedBlackBST<>();
        int[] arr = new int[R];
        for (char i = 0; i < R; i++) {
            bst.put((int) i, i);
            arr[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int key = arr[c];
            BinaryStdOut.write((char) bst.rank(key));
            bst.delete(key);
            arr[c] = bst.min() - 1;
            bst.put(arr[c], c);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        RedBlackBST<Integer, Character> bst = new RedBlackBST<>();
        for (char i = 0; i < R; i++) {
            bst.put((int) i, i);
        }
        while (!BinaryStdIn.isEmpty()) {
            int x = BinaryStdIn.readChar();
            Integer key = bst.select(x);
            char c = bst.get(key);
            BinaryStdOut.write(c);
            bst.delete(key);
            bst.put(bst.min() - 1, c);
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        String s = StdIn.readLine();
        if (s.equals("-")) encode();
        else if (s.equals("+")) decode();
        else throw new IllegalArgumentException("Illegal input");
    }
}
