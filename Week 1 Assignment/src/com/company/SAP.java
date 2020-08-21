package com.company;

import edu.princeton.cs.algs4.*;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("argument is null");
        }
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        check(v, w);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return result(bfsV, bfsW)[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        check(v, w);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return result(bfsV, bfsW)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkIterable(v, w);

        int c = 0, r = 0;
        for (int ignored : v) {
            c++;
        }
        for (int ignored : w) {
            r++;
        }

        if (c == 0 || r == 0) {
            return -1;
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return result(bfsV, bfsW)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkIterable(v, w);

        int c = 0, r = 0;
        for (int ignored : v) {
            c++;
        }
        for (int ignored : w) {
            r++;
        }

        if (c == 0 || r == 0) {
            return -1;
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return result(bfsV, bfsW)[1];
    }

    private void check(int v, int w) {
        if (v < 0 || w < 0 || v > digraph.V() - 1 || w > digraph.V() - 1) {
            throw new IllegalArgumentException("not in range");
        }
    }

    private void checkIterable(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (Integer val : v) {
            if (val == null) {
                throw new IllegalArgumentException("value inside iterable is invalid");
            }
        }
        for (Integer val : w) {
            if (val == null) {
                throw new IllegalArgumentException("value inside iterable is invalid");
            }
        }
    }

    private int[] result(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int[] res = new int[2];
        int len = -1;
        int p = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int lv = bfsV.distTo(i);
                int wv = bfsW.distTo(i);
                if (lv + wv < len || len == -1) {
                    len = lv + wv;
                    p = i;
                }
            }
        }
        res[0] = len;
        res[1] = p;
        return res;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        //test 1(single source)
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }

        //test 2(multiple source(iterable))
        while (!StdIn.isEmpty()) {
            int m = StdIn.readInt();
            int n = StdIn.readInt();
            Stack<Integer> v = new Stack<>();
            Stack<Integer> w = new Stack<>();
            for (int i = 0; i < m; i++) {
                v.push(StdIn.readInt());
            }
            for (int i = 0; i < n; i++) {
                w.push(StdIn.readInt());
            }
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
