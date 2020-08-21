package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDirectedCycle;

public class BellmanFordSP {

    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final boolean[] onQueue;
    private final Queue<Integer> queue;
    private Iterable<DirectedEdge> cycle;
    private int cost;

    public BellmanFordSP(EdgeWeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];
        onQueue = new boolean[g.V()];
        cost = 0;

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MAX_VALUE;
        }
        distTo[s] = 0.0;

        queue = new Queue<>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(g, v);
        }
    }

    private void relax(EdgeWeightedDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (++cost % g.V() == 0) {     // here modulo because cost counter can be at most E*V which is > V
                findNegativeCycle();
                if (hasNegativeCycle()) {
                    return;
                }
            }
        }
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(V);
        for (DirectedEdge directedEdge : edgeTo) {
            if (directedEdge != null) {
                g.addEdge(directedEdge);
            }
        }
        EdgeWeightedDirectedCycle d = new EdgeWeightedDirectedCycle(g);
        cycle = d.cycle();
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.MAX_VALUE;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (hasNegativeCycle()) {
            throw new IllegalArgumentException("negative cycle exists");
        }
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
        In in = new In(StdIn.readLine());
        int s = Integer.parseInt(StdIn.readLine());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        BellmanFordSP sp = new BellmanFordSP(G, s);

        // print negative cycle
        if (sp.hasNegativeCycle()) {
            System.out.println("negative cycle exists:");
            for (DirectedEdge e : sp.negativeCycle())
                StdOut.println(e);
        }

        // print shortest paths
        else {
            for (int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                    for (DirectedEdge e : sp.pathTo(v)) {
                        StdOut.print(e + "   ");
                    }
                    StdOut.println();
                } else {
                    StdOut.printf("%d to %d           no path\n", s, v);
                }
            }
        }
    }
}
