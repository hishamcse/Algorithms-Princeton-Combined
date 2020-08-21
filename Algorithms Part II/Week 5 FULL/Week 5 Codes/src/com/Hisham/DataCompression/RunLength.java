package com.Hisham.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;

public class RunLength {

    private static final int R = 256;
    private static final int LG_R = 8;

    public static void expand() {
        boolean b = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(LG_R);
            for (int i = 0; i < run; i++) {
                BinaryStdOut.write(b);
            }
            b = !b;
        }
        BinaryStdOut.close();
    }

    public static void compress() {
        char run = 0;
        boolean old = false;
        while (!BinaryStdIn.isEmpty()) {
            boolean b = BinaryStdIn.readBoolean();
            if (b != old) {
                BinaryStdOut.write(run, LG_R);
                run = 1;
                old = !old;
            } else {
                if (run == R - 1) {
                    BinaryStdOut.write(run, LG_R);
                    run = 0;
                    BinaryStdOut.write(run, LG_R);
                }
                run++;
            }
        }
        BinaryStdOut.write(run, LG_R);
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        String s = StdIn.readLine();
        if (s.equals("-")) compress();
        else if (s.equals("+")) expand();
        else throw new IllegalArgumentException("Illegal input");
    }
}
