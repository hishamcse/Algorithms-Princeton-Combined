package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;

public class DijkstraSP {

    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final IndexMinPQ<Double> minPQ;

    public DijkstraSP(EdgeWeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];
        minPQ = new IndexMinPQ<>(g.V());

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MAX_VALUE;
        }

        distTo[s] = 0.0;
        minPQ.insert(s, distTo[s]);
        while (!minPQ.isEmpty()) {
            int v = minPQ.delMin();
            for (DirectedEdge e : g.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (minPQ.contains(w)) {
                minPQ.decreaseKey(w, distTo[w]);
            } else {
                minPQ.insert(w, distTo[w]);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.MAX_VALUE;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if(!hasPathTo(v)){
            return null;
        }
        Stack<DirectedEdge> stack = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            stack.push(e);
        }
        return stack;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(StdIn.readString());

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, s);

        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }

}
