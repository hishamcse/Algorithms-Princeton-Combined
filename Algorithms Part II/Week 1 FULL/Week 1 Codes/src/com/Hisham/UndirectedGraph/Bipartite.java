package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Graph;

public class Bipartite {

    // Given a graph, find either (i) a Bipartition or (ii) an odd-length cycle.
    // Application: dating graph for STD(Sexually Transmitted Diseases).this graph should be bipartite as
    // the graph always connected to a male to a female.So,the two partition will be men & women.

    private final boolean[] marked;
    private final boolean[] color;
    private final int[] edgeTo;
    private boolean isBipartite;
    private Stack<Integer> cycle;    // odd length cycle if not bipartite

    public Bipartite(edu.princeton.cs.algs4.Graph g) {
        isBipartite = true;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        color = new boolean[g.V()];
        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) {
                dfs(g, v);
            }
        }
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (cycle != null) {
                return;
            }
            if (!marked[w]) {
                edgeTo[w] = v;
                color[w] = !color[v];
                dfs(g, w);
            } else if (color[w] == color[v]) {
                isBipartite = false;
                cycle = new Stack<>();
                cycle.push(w);     // it will be twice because we want cycle
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
            }
        }
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    public Iterable<Integer> oddCycle() {
        return cycle;
    }

    public boolean color(int v) {
        if (!isBipartite()) {
            throw new UnsupportedOperationException("not allowed");
        }
        return color[v];
    }

    public static void main(String[] args) {

        //test 1(user input)
        int V1 = Integer.parseInt(StdIn.readLine());
        int V2 = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        int F = Integer.parseInt(StdIn.readLine());

        // create random bipartite graph with V1 vertices on left side,
        // V2 vertices on right side, and E edges; then add F random edges
        edu.princeton.cs.algs4.Graph G = GraphGenerator.bipartite(V1, V2, E);
        for (int i = 0; i < F; i++) {
            int v = StdRandom.uniform(V1 + V2);
            int w = StdRandom.uniform(V1 + V2);
            G.addEdge(v, w);
        }

        Bipartite b = new Bipartite(G);
        StdOut.println(b.isBipartite());
        if (!b.isBipartite()) {
            for (int s : b.oddCycle()) {
                System.out.print(s + " ");
            }
        } else {
            for (int v = 0; v < G.V(); v++) {
                System.out.println(v + " " + b.color(v));
            }
        }

        System.out.println();

        //test 2(bipartite.txt)
        In in = new In(StdIn.readLine());
        Graph g = new Graph(in);

        Bipartite b1 = new Bipartite(g);
        StdOut.println(b1.isBipartite());
        if (!b1.isBipartite()) {
            for (int s : b1.oddCycle()) {
                System.out.print(s + " ");
            }
        } else {
            for (int v = 0; v < G.V(); v++) {
                System.out.println(v + " " + b1.color(v));
            }
        }
    }

}
