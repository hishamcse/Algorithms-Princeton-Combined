package com.Hisham;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.math.BigInteger;
import java.util.Random;

public class Karatsuba {

    public static BigInteger karatsuba(BigInteger x, BigInteger y) {

        int n = Math.max(x.bitLength(), y.bitLength());
        if (n <= 2000) {
            return x.multiply(y);
        }

        n = (n / 2) + (n % 2);

        BigInteger b = x.shiftRight(n);
        BigInteger a = x.subtract(b.shiftLeft(n));
        BigInteger d = y.shiftRight(n);
        BigInteger c = y.subtract(d.shiftLeft(n));

        BigInteger ac = karatsuba(a, c);
        BigInteger bd = karatsuba(b, d);
        BigInteger abcd = karatsuba(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(n)).add(bd.shiftLeft(2 * n));
    }

    public static void main(String[] args) {
        long start, stop;
        Random random = new Random();
        int N = Integer.parseInt(StdIn.readLine());
        BigInteger a = new BigInteger(N, random);
        BigInteger b = new BigInteger(N, random);

        start = System.currentTimeMillis();
        BigInteger c = karatsuba(a, b);
        stop = System.currentTimeMillis();
        StdOut.println(stop - start);

        start = System.currentTimeMillis();
        BigInteger d = a.multiply(b);
        stop = System.currentTimeMillis();
        StdOut.println(stop - start);

        StdOut.println((c.equals(d)));

        System.out.println("first number = " + a);
        System.out.println("second number = " + b);
        System.out.println("product = " + karatsuba(a, b));
    }
}
