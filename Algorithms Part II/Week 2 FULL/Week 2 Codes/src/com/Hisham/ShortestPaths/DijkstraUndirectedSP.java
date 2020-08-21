package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;

public class DijkstraUndirectedSP {

    private final Edge[] edgeTo;
    private final double[] distTo;
    private final IndexMinPQ<Double> minPQ;

    public DijkstraUndirectedSP(EdgeWeightedGraph g, int s) {
        edgeTo = new Edge[g.V()];
        distTo = new double[g.V()];
        minPQ = new IndexMinPQ<>(g.V());

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MAX_VALUE;
        }

        distTo[s] = 0.0;
        minPQ.insert(s, distTo[s]);
        while (!minPQ.isEmpty()) {
            int v = minPQ.delMin();
            for (Edge e : g.adj(v)) {
                relax(e, v);
            }
        }
    }

    private void relax(Edge e, int v) {
        int w = e.other(v);
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

    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Edge> stack = new Stack<>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            stack.push(e);
            x = e.other(x);
        }
        return stack;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        int s = Integer.parseInt(StdIn.readString());

        // compute shortest paths
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, s);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (Edge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }

}
