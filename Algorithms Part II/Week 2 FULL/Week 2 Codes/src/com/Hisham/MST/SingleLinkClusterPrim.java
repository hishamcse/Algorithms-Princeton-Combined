package com.Hisham.MST;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class SingleLinkClusterPrim {

    private double weight;
    private final MinPQ<Edge> minPQ;
    private final Stack<Edge> mst;
    private final boolean[] marked;
    private final int k;
    private final int count;

    public SingleLinkClusterPrim(EdgeWeightedGraph g, int k) {
        marked = new boolean[g.V()];
        minPQ = new MinPQ<>();
        mst = new Stack<>();
        weight = 0;
        count = g.V();
        this.k = k;

        visit(g, 0);

        while (!minPQ.isEmpty() && mst.size() < g.V() - 1) {
            edu.princeton.cs.algs4.Edge e = minPQ.delMin();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) {
                continue;
            }
            mst.push(e);
            if (!marked[v]) {
                visit(g, v);
            }
            if (!marked[w]) {
                visit(g, w);
            }
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            if (!marked[e.other(v)]) {
                minPQ.insert(e);
            }
        }
    }

    public double weight() {   // should be called after edges().otherwise will give the wrong answer
        for (Edge e : mst) {
            weight += e.weight();
        }
        return weight;
    }

    public Iterable<Edge> edges() {
        for (int i = 0; i < k - 1; i++) {
            mst.pop();
        }
        return mst;
    }

    public int count() {
        return count - mst.size();
    }

    public static void main(String[] args) {
        int k = StdIn.readInt();
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        SingleLinkClusterPrim mst = new SingleLinkClusterPrim(G, k);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
        System.out.println(mst.count());
    }
}
