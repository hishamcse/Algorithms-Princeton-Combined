package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.*;

public class FattestPath {   // using the modified dijkstra sp

    private final edu.princeton.cs.algs4.DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final IndexMaxPQ<Double> maxPQ;

    public FattestPath(EdgeWeightedDigraph g, int s) {
        edgeTo = new edu.princeton.cs.algs4.DirectedEdge[g.V()];
        distTo = new double[g.V()];
        maxPQ = new IndexMaxPQ<>(g.V());

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.NEGATIVE_INFINITY;
        }

        distTo[s] = Double.POSITIVE_INFINITY;
        maxPQ.insert(s, distTo[s]);
        while (!maxPQ.isEmpty()) {
            int v = maxPQ.delMax();
            for (edu.princeton.cs.algs4.DirectedEdge e : g.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(edu.princeton.cs.algs4.DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] < Math.min(distTo[v] ,e.weight())) {
            distTo[w] = Math.min(distTo[v] ,e.weight());
            edgeTo[w] = e;
            if (maxPQ.contains(w)) {
                maxPQ.increaseKey(w, distTo[w]);
            } else {
                maxPQ.insert(w, distTo[w]);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] > Double.NEGATIVE_INFINITY;
    }

    public Iterable<edu.princeton.cs.algs4.DirectedEdge> pathTo(int v) {
        if(!hasPathTo(v)){
            return null;
        }
        Stack<edu.princeton.cs.algs4.DirectedEdge> stack = new Stack<>();
        for (edu.princeton.cs.algs4.DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            stack.push(e);
        }
        return stack;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(StdIn.readString());

        // compute shortest paths
        FattestPath sp = new FattestPath(G, s);

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
