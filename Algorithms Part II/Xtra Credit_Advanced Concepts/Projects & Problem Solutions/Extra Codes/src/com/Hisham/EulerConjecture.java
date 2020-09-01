package com.Hisham;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class EulerConjecture {

// *  Tests whether there are any four positive integers that satisfy
// *  a^4 + b^4 + c^4 = d^4. Euler conjectured that no such solutions
// *  exists, but his conjecture was later disproved.
// *
// *  Mathematical shortcut: can assume
// *    - a is a multiple of 8
// *    - b is a multiple of 40
// *    - d-1 is a multiple of 8
// *    - either c+d is a multiple of 1024 or d-c is a multiple of 1024
// *     - d is not a multiple of 5

    //    static long[] four;       // table of fourth powers
    static Item[] st;         // symbol table - hash table

    static int hash(long sum) {
        return (int) (sum) >>> 7;
    }

    /***************************************************************************
     *  Insert item into ST.
     ***************************************************************************/
    static void insert(Item x) {
        int i = hash(x.sum);
        while (st[i] != null) {
            i++;
            if (i >= st.length) i -= st.length;
        }
        st[i] = x;
    }

    /***************************************************************************
     *  Search ST for given key.
     ***************************************************************************/
    static Item search(long sum) {
        int i = hash(sum);
        while (st[i] != null) {
            if (st[i].sum == sum) return st[i];
            i++;
            if (i >= st.length) i -= st.length;
        }
        return null;
    }


    /***************************************************************************
     *  Item to store in hash table.
     ***************************************************************************/
    private static class Item {
        int d, c;
        long sum;    // d^4 - c^4  (mod 2^64)

        Item(int d, int c, long sum) {
            this.d = d;
            this.c = c;
            this.sum = sum;
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(StdIn.readLine());
        long a4, b4, c4, d4;
        st = new Item[33554432];   // 2^25

        /*Precompute i^4 (mod 2^64) by relying on fact that overflow
          of long in Java is automatically done this way.
         */
        long[] four = new long[N + 1];
        for (int i = 0; i <= N; i++) {
            four[i] = i;
            four[i] *= four[i];
            four[i] *= four[i];
        }
        StdOut.println("Done precomputing table of fourth powers");

        /* Enumerate of all pairs (c, d) and put d^4 - c^4 (mod 2^64)
         * in a hash table along with c and d.
         */
        for (int d = 1; d < N; d += 8) {
            if (d % 5 == 0) continue;
            d4 = four[d];
            for (int c = d % 1024; c < d; c += 1024) {
                c4 = four[c];
                insert(new Item(d, c, d4 - c4));
            }
            for (int c = 1024 - d % 1024; c < d; c += 1024) {
                c4 = four[c];
                insert(new Item(d, c, d4 - c4));
            }
        }

        StdOut.println("Done building hash table");

        /* Enumerate of all pairs (a, b) and search in a hash table
         * for a^4 + b^4 (mod 2^64). If match, perform full precision
         * check to see if a^4 + b^4 = d^4 - c^4
         */
        for (int a = 8; a < N; a += 8) {
            a4 = four[a];
            if (a % 10000 == 0) StdOut.println(a);
            for (int b = 40; b < N; b += 40) {
                b4 = four[b];
                if (search(a4 + b4) != null) {
                    Item item = search(a4 + b4);
                    StdOut.print(a + "^4 + " + b + "^4 + ");
                    assert item != null;
                    StdOut.println(item.c + "^4 = " + item.d + "^4");
                }
            }
        }
    }
}
