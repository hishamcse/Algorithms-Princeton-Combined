package com.Hisham.SubStringSearch;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {

    private final String pat;  // needed for only las vagas version
    private final long patHash;
    private final int m;
    private final long q;
    private final int R;
    private long RM;

    public RabinKarp(String pattern) {
        m = pattern.length();
        R = 256;
        q = longRandomPrime();
        pat=pattern;

        RM = 1;
        for (int i = 1; i < m; i++) {
            RM = (R * RM) % q;
        }
        patHash = hash(pattern, m);
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    private long hash(String pattern, int m) {
        long h = 0;
        for (int i = 0; i < m; i++) {
            h = ((R * h) + pattern.charAt(i)) % q;
        }
        return h;
    }

    // las vagas version to check
    private boolean checkLasVagas(String text, int i) {
        for (int j = 0; j < m; j++) {
            if (pat.charAt(j) != text.charAt(i + j)) {
                return false;
            }
        }
        return true;
    }

    public int search(String key) {
        int n = key.length();
        long textHash = hash(key, m);
        if (textHash == patHash && checkLasVagas(key, 0)) {   // textHash==patHash is monte carlo check
            return 0;
        }
        for (int i = m; i < n; i++) {
            textHash = textHash + (q - RM) * key.charAt(i - m);
//            textHash=(textHash + q - RM*key.charAt(i-m) % q) % q;
            textHash = (textHash * R + key.charAt(i)) % q;
            if (textHash == patHash && checkLasVagas(key, i-m+1)) {
                return i-m+1;
            }
        }
        return -1; // not found
    }

    public static void main(String[] args) {
        String pat = StdIn.readLine();
        String txt = StdIn.readLine();

        RabinKarp searcher = new RabinKarp(pat);
        int offset = searcher.search(txt);

        // print results
        StdOut.println("text:    " + txt);

        // from brute force search method 1
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
