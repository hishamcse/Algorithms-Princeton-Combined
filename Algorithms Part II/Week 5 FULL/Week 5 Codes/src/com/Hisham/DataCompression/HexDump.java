package com.Hisham.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class HexDump {

    public static void main(String[] args) {
        int bytesPerLine = 16;
        bytesPerLine = Integer.parseInt(StdIn.readLine());

        int i;
        for (i = 0; !BinaryStdIn.isEmpty(); i++) {
            if (bytesPerLine == 0) {
                BinaryStdIn.readChar();
                continue;
            }
            if (i == 0) StdOut.printf("");
            else if (i % bytesPerLine == 0) StdOut.printf("\n", i);
            else StdOut.print(" ");
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        if (bytesPerLine != 0) StdOut.println();
        StdOut.println((i*8) + " bits");
    }
}
