package com.Hisham.MST;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class SingleLinkCluster {
    private double weight;
    private final Queue<Edge> mst;  // we use queue so that can be inserted and accessed in the same order
    private int count;

    public SingleLinkCluster(EdgeWeightedGraph g, int k) {
        mst = new Queue<>();
        weight = 0;

        // we could use an array then sort that.But as MinPQ already takes care of the ascending sort of the elements
        // that implements Comparable.So,as Edge implements Comparable,so,for simplicity and compact coding we use MinPQ
        MinPQ<Edge> minPQ = new MinPQ<>();
        for (Edge e : g.edges()) {
            minPQ.insert(e);
        }

        UF uf = new UF(g.V());
        count = 0;
        Graph r = new Graph(g.V());

        while (!minPQ.isEmpty() && count != k) {
            Edge e = minPQ.delMin();
            int v = e.either();
            int w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
                r.addEdge(v, w);
                CC cc = new CC(r);
                count = cc.count();
            }
        }
    }

    public int count() {
        return count;
    }

    public double weight() {
        return weight;
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public static void main(String[] args) {
        int k = StdIn.readInt();
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        SingleLinkCluster mst = new SingleLinkCluster(G, k);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
        System.out.println(mst.count());
    }
}
