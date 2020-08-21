package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class DirectedEulerianPath {

    private Stack<Integer> path = null;

    public DirectedEulerianPath(Digraph g) {
        int s = nonIsolatedVertex(g);
        int odd = 0;
        for (int v = 0; v < g.V(); v++) {
            if (g.outdegree(v) > g.indegree(v)) {
                odd += (g.outdegree(v) - g.indegree(v));
                s = v;
            }
        }

        if (odd > 1) {
            return;
        }
        if (s == -1) {     // if there is no vertex having at least one edge
            s = 0;
        }

        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[g.V()];
        for (int v = 0; v < g.V(); v++) {
            adj[v] = g.adj(v).iterator();
        }

        createPath(g, adj, s);
    }


    private void createPath(Digraph g, Iterator<Integer>[] adj, int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        path = new Stack<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            path.push(v);
        }

        if (path.size() != g.E() + 1) {
            path = null;
        }
    }

    private int nonIsolatedVertex(Digraph g) {
        for (int v = 0; v < g.V(); v++) {
            if (g.outdegree(v) > 0) {
                return v;
            }
        }
        return -1;
    }

    public boolean hasEulerianPath() {
        return path != null;
    }

    public Iterable<Integer> path() {
        return path;
    }


    private static void unitTest(Digraph G, String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);

        DirectedEulerianPath euler = new DirectedEulerianPath(G);

        StdOut.print("Eulerian path:  ");
        if (euler.hasEulerianPath()) {
            for (int v : euler.path()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("none");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());

        // Eulerian cycle
        Digraph G1 = DigraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");

        // Eulerian path
        Digraph G2 = DigraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");

        // add one random edge
        Digraph G3 = new Digraph(G2);
        G3.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G3, "one random edge added to Eulerian path");

        // self loop
        Digraph G4 = new Digraph(V);
        int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");

        // single edge
        Digraph G5 = new Digraph(V);
        G5.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G5, "single edge");

        // empty digraph
        Digraph G6 = new Digraph(V);
        unitTest(G6, "empty digraph");

        // random digraph
        Digraph G7 = DigraphGenerator.simple(V, E);
        unitTest(G7, "simple digraph");

    }
}
