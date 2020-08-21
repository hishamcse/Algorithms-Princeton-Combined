package com.Hisham.MST;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class BoruvkaMST {

    private final Queue<Edge> mst;
    private double weight;

    public BoruvkaMST(EdgeWeightedGraph g) {
        weight = 0;
        mst = new Queue<>();

        UF uf = new UF(g.V());

        // repeat at most log V times or until we have V-1 edges
        for (int t = 1; t < g.V() && mst.size() < g.V() - 1; t = t + t) {
            Edge[] closest = new Edge[g.V()];

            // setting the minimum weighted edges for every vertex
            for (Edge e : g.edges()) {
                int v = e.either();
                int w = e.other(v);
                int i = uf.find(v);
                int j = uf.find(w);
                if (i == j) {
                    continue;
                }
                if (closest[i] == null || e.compareTo(closest[i]) < 0) {
                    closest[i] = e;
                }
                if (closest[j] == null || e.compareTo(closest[j]) < 0) {
                    closest[j] = e;
                }
            }

            // according to the minimum weighted edges stored previously,processing to make mst
            for (int i = 0; i < g.V(); i++) {
                Edge e = closest[i];
                if (e != null) {
                    int v = e.either();
                    int w = e.other(v);
                    if (uf.find(v) != uf.find(w)) {
                        mst.enqueue(e);
                        uf.union(v, w);
                        weight += e.weight();
                    }
                }
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
        BoruvkaMST mst = new BoruvkaMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
