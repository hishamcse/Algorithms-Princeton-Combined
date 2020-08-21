package com.Hisham.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.TST;

public class LZWCompression {

    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    public static void compress() {

        String input = BinaryStdIn.readString();

        TST<Integer> tst = new TST<>();
        for (int i = 0; i < R; i++) {
            tst.put("" + (char) i, i);
        }

        int code = R + 1;    // stop point + 1 starting from this index,the compressed strings will be stored

        while (input.length() > 0) {
            String s = tst.longestPrefixOf(input);
            BinaryStdOut.write(tst.get(s), W);
            int t = s.length();
            if (t < input.length() && code < L) {
                tst.put(input.substring(0, t + 1), code++);   // code is where the compressed strings will be stored
            }
            input = input.substring(t);   // now scan the next substring(t,R)
        }

        BinaryStdOut.write(R, W);  // stop point
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L];
        int i;

        for (i = 0; i < R; i++) {
            st[i] = "" + (char) i;
        }
        st[i++] = "";

        int codeWord = BinaryStdIn.readInt(W);
        if (codeWord == R) {                  // expanded message is empty string
            return;
        }
        String val = st[codeWord];

        while (true) {
            BinaryStdOut.write(val);
            codeWord = BinaryStdIn.readInt(W);
            if (codeWord == R) {              // expanded message is empty string
                break;
            }
            String s = st[codeWord];
            if (i == codeWord) {      // special case hack
                s = val + val.charAt(0);
            }
            if (i < L) {
                st[i++] = val + s.charAt(0);
            }
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        String s = StdIn.readLine();
        if (s.equals("-")) compress();
        else if (s.equals("+")) expand();
        else throw new IllegalArgumentException("Illegal input");
    }
}
