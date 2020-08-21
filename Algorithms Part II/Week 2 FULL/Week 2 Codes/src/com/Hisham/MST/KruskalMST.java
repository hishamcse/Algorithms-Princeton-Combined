package com.Hisham.MST;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class KruskalMST {

    private double weight;
    private final Queue<Edge> mst;  // we use queue so that can be inserted and accessed in the same order

    public KruskalMST(EdgeWeightedGraph g) {
        mst = new Queue<>();
        weight = 0;

        // we could use an array then sort that.But as MinPQ already takes care of the ascending sort of the elements
        // that implements Comparable.So,as Edge implements Comparable,so,for simplicity and compact coding we use MinPQ
        MinPQ<Edge> minPQ = new MinPQ<>();
        for (Edge e : g.edges()) {
            minPQ.insert(e);
        }

        UF uf = new UF(g.V());

        while (!minPQ.isEmpty() && mst.size() < g.V() - 1) {
            Edge e = minPQ.delMin();
            int v = e.either();
            int w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
            }
        }
    }

    public double weight() {
        return weight;
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst = new KruskalMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
