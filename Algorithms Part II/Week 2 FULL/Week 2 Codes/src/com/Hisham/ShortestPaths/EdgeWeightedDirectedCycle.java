package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;

public class EdgeWeightedDirectedCycle {

    private final boolean[] marked;
    private final DirectedEdge[] edgeTo;
    private Stack<DirectedEdge> cycle;
    private final boolean[] onCycle;

    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph g) {
        marked = new boolean[g.V()];
        edgeTo = new DirectedEdge[g.V()];
        onCycle = new boolean[g.V()];

        for (int v = 0; v < g.V(); v++) {
            if (cycle == null && !marked[v]) {
                dfs(g, v);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph g, int v) {
        onCycle[v] = true;
        marked[v] = true;
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (cycle != null) {
                return;
            } else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(g, w);
            } else if (onCycle[w]) {
                cycle = new Stack<>();
                DirectedEdge x;
                for (x = e; x.from() != w; x = edgeTo[e.from()]) {
                    cycle.push(x);
                }
                cycle.push(x);
                return;
            }
        }
        onCycle[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    public static void main(String[] args) {

        // create random DAG with V vertices and E edges; then add F random edges
        int V = Integer.parseInt(StdIn.readString());
        int E = Integer.parseInt(StdIn.readString());
        int F = Integer.parseInt(StdIn.readString());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < E; i++) {
            int v, w;
            do {
                v = StdRandom.uniform(V);
                w = StdRandom.uniform(V);
            } while (v >= w);
            double weight = StdRandom.uniform();
            G.addEdge(new DirectedEdge(v, w, weight));
        }

        // add F extra edges
        for (int i = 0; i < F; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = StdRandom.uniform(0.0, 1.0);
            G.addEdge(new DirectedEdge(v, w, weight));
        }

        StdOut.println(G);

        // find a directed cycle
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Cycle: ");
            for (DirectedEdge e : finder.cycle()) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }

        // or give topological sort
        else {
            StdOut.println("No directed cycle");
        }
    }
}
