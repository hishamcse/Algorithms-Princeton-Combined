package com.Hisham.Regex;

import edu.princeton.cs.algs4.StdOut;

public class Tokenizer {

    public static void main(String[] args) {
        String s = "/this is/a test of the/class// and/all//last";
        char delimiter = '/';

        // calculate number of delimiter characters
        int n = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == delimiter) n++;
        String[] tokens = new String[n + 1];

        // parse n+1 tokens and store in an array
        int right = 0, left = 0;
        for (int i = 0; i < n; i++) {
            while (s.charAt(right) != delimiter)
                right++;
            tokens[i] = s.substring(left, right);
            right++;
            left = right;
        }
        tokens[n] = s.substring(right);

        // print results for testing
        for (int i = 0; i < tokens.length; i++)
            StdOut.println(i + ": " + tokens[i]);
    }
}
