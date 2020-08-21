package com.Hisham.MST;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class EagerPrimMST {

    private final boolean[] marked;
    private final Edge[] edgeTo;
    private final double[] distTo;
    private final IndexMinPQ<Double> minPQ;

    public EagerPrimMST(EdgeWeightedGraph g) {
        marked = new boolean[g.V()];
        edgeTo = new Edge[g.V()];
        distTo = new double[g.V()];
        minPQ = new IndexMinPQ<>(g.V());

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MAX_VALUE;
        }
        prim(g);
    }

    private void prim(EdgeWeightedGraph g) {
        distTo[0] = 0.0;
        minPQ.insert(0, distTo[0]);
        while (!minPQ.isEmpty()) {
            int v = minPQ.delMin();
            visit(g, v);
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);
            if (marked[w]) {
                continue;
            }
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (minPQ.contains(w)) {
                    minPQ.decreaseKey(w, distTo[w]);
                } else {
                    minPQ.insert(w, distTo[w]);
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<>();
        for (Edge e : edgeTo) {
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges()) {
            weight += e.weight();
        }
        return weight;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        EagerPrimMST mst = new EagerPrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
