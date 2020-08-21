package com.Hisham.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

public class HuffmanCompression {

    private static final int R = 256;

    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left;
        private final Node right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.freq, that.freq);
        }
    }

    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        int[] freq = new int[R];

        for (char c : input) {
            freq[c]++;
        }

        Node root = buildTrie(freq);

        String[] st = new String[R];
        buildCode(st, root, "");

        writeTrie(root);

        BinaryStdOut.write(input.length);

        for (char c : input) {
            String code = st[c];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                }
                if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                }
            }
        }
        BinaryStdOut.close();
    }

    private static Node buildTrie(int[] freq) {
        MinPQ<Node> minPQ = new MinPQ<>();
        for (char c = 0; c < R; c++) {
            if (freq[c] > 0) {
                minPQ.insert(new Node(c, freq[c], null, null));
            }
        }
        while (minPQ.size() > 1) {
            Node left = minPQ.delMin();
            Node right = minPQ.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            minPQ.insert(parent);
        }
        return minPQ.delMin();
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left, s + '0');
            buildCode(st, x.right, s + '1');
        } else {
            st[x.ch] = s;
        }
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    public static void expand() {

        Node root = readTrie();

        int n = BinaryStdIn.readInt();

        for (int i = 0; i < n; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) {
                    x = x.right;
                } else {
                    x = x.left;
                }
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new Node(BinaryStdIn.readChar(), -1, null, null);
        }
        return new Node('\0', -1, readTrie(), readTrie());
    }

    public static void main(String[] args) {
        String s = StdIn.readLine();
        if (s.equals("-")) compress();
        else if (s.equals("+")) expand();
        else throw new IllegalArgumentException("Illegal input");
    }

}
