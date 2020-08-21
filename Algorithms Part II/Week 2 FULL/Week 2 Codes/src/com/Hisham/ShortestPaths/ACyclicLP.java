package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Topological;

public class ACyclicLP {

    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    public ACyclicLP(EdgeWeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MIN_VALUE;   // negating the weights by minimum value of double
        }
        distTo[s] = 0.0;

        Topological topological = new Topological(g);
        if (!topological.hasOrder()) {
            throw new IllegalArgumentException("is not a edge Weighted DAG");
        }
        for (int v : topological.order()) {
            for (DirectedEdge e : g.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] < distTo[v] + e.weight()) {   // as Longest path,so reverse the condition
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] > Double.MIN_VALUE;
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
        int s = Integer.parseInt(StdIn.readString());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        ACyclicLP lp = new ACyclicLP(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (lp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)  ", s, v, lp.distTo(v));
                for (DirectedEdge e : lp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, v);
            }
        }
    }
}
