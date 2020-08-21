package com.Hisham.MST;

import edu.princeton.cs.algs4.*;

public class MinWeightEdgeFeedbackKruskal {

    private double weight;
    private final Queue<Edge> mst;  // we use queue so that can be inserted and accessed in the same order

    public MinWeightEdgeFeedbackKruskal(EdgeWeightedGraph g) {
        mst = new Queue<>();
        weight = 0;

        // we could use an array then sort that.But as MaxPQ already takes care of the ascending sort of the elements
        // that implements Comparable.So,as Edge implements Comparable,so,for simplicity and compact coding we use MaxPQ
        MaxPQ<Edge> maxPQ = new MaxPQ<>();
        for (Edge e : g.edges()) {
            maxPQ.insert(e);
        }

        UF uf = new UF(g.V());

        while (!maxPQ.isEmpty()) {
            Edge e = maxPQ.delMax();
            int v = e.either();
            int w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
            } else {
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

    public int size(){
        return mst.size();
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        MinWeightEdgeFeedbackKruskal mst = new MinWeightEdgeFeedbackKruskal(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
        System.out.println("size = "+mst.size());
    }
}
