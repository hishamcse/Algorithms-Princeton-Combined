package com.Hisham.Regex;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CommentStripper {    // parsing a .java or c++ file

    public static void main(String[] args) {
        final int CODE = 0;     // parsing normal code
        final int SLASH = 1;    // found a leading '/'
        final int BLOCK = 2;    // in a block comment
        final int LINE = 3;     // in a line comment
        final int STAR = 4;     // found a trailing * in a block comment

        int state = CODE;      // current state

        while (!StdIn.isEmpty()) {
            char c = StdIn.readChar();
            switch (state) {
                case CODE:
                    if (c == '/') {
                        state = SLASH;
                    } else {
                        StdOut.print(c);
                    }
                    break;

                case SLASH:
                    if (c == '*') {
                        state = BLOCK;
                    } else if (c == '/') {
                        state = LINE;
                    } else {
                        state = CODE;
                        StdOut.print("/" + c);
                    }
                    break;

                case BLOCK:
                    if (c == '*') {
                        state = STAR;
                    }
                    break;

                case STAR:
                    if (c == '/') {
                        state = CODE;
                        StdOut.print(" ");
                    } else if (c == '*') {
                        state = STAR;
                    } else {
                        state = BLOCK;
                    }
                    break;

                case LINE:
                    if (c == '\n') {
                        state = CODE;
                        StdOut.println();
                    }
                    break;
            }
        }
    }
}
