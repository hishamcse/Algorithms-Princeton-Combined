package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;

import java.util.Comparator;

public class LazyDijkstraSP {

    private final boolean[] marked;
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final MinPQ<DirectedEdge> minPQ;

    private class ByDistanceFromSource implements Comparator<DirectedEdge> {
        public int compare(DirectedEdge e, DirectedEdge f) {
            double dist1 = distTo[e.from()] + e.weight();
            double dist2 = distTo[f.from()] + f.weight();
            return Double.compare(dist1, dist2);
        }
    }

    public LazyDijkstraSP(EdgeWeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];
        minPQ = new MinPQ<>(new ByDistanceFromSource());
        marked = new boolean[g.V()];

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MAX_VALUE;
        }

        distTo[s] = 0.0;
        relax(g, s);

        while (!minPQ.isEmpty()) {
            DirectedEdge e = minPQ.delMin();
            int w = e.to();
            if (!marked[w]) {
                relax(g, w);
            }
        }
    }

    private void relax(EdgeWeightedDigraph g, int v) {
        marked[v] = true;
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                minPQ.insert(e);
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
        if (!hasPathTo(v)) {
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
        LazyDijkstraSP sp = new LazyDijkstraSP(G, s);

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
