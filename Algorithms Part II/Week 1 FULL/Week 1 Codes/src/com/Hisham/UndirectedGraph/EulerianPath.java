package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Graph;

public class EulerianPath {

    private Stack<Integer> path;

    private static class Edge {
        private final int v;
        private final int w;
        private boolean isUsed;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
            isUsed = false;
        }

        public int other(int s) {
            if (s == v) {
                return w;
            } else if (s == w) {
                return v;
            } else {
                throw new IllegalArgumentException("argument illegal");
            }
        }
    }

    public EulerianPath(Graph g) {
        int odd = 0;
        int s = nonIsolatedVertex(g);
        for (int v = 0; v < g.V(); v++) {
            if (g.degree(v) % 2 != 0) {
                odd++;
                s = v;      //because we need an odd length vertex to start the eulerian path
            }
        }

        if (odd > 2) {
            return;
        }
        if (s == -1) {     //if there is no vertex having at least one edge
            s = 0;
        }

        Queue<Edge>[] adj = (Queue<Edge>[]) new Queue[g.V()];
        createLocalAdjList(g, adj);

        createPath(g, adj, s);
    }

    // create local view of adjacency lists, to iterate one vertex at a time
    // the helper Edge data type is used to avoid exploring both copies of an edge v-w
    private void createLocalAdjList(edu.princeton.cs.algs4.Graph g, Queue<Edge>[] adj) {
        for (int v = 0; v < g.V(); v++) {
            adj[v] = new Queue<>();
        }
        for (int v = 0; v < g.V(); v++) {
            int selfLoops = 0;
            for (int w : g.adj(v)) {
                if (v == w) {
                    if (selfLoops % 2 == 0) {
                        Edge e = new Edge(v, w);
                        adj[v].enqueue(e);
                        adj[w].enqueue(e);
                    }
                    selfLoops++;
                } else if (v < w) {
                    Edge e = new Edge(v, w);
                    adj[v].enqueue(e);
                    adj[w].enqueue(e);
                }
            }
        }
    }

    private void createPath(Graph g, Queue<Edge>[] adj, int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        path = new Stack<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (!adj[v].isEmpty()) {
                Edge edge = adj[v].dequeue();
                if (edge.isUsed) {
                    continue;
                }
                edge.isUsed = true;
                stack.push(v);
                v = edge.other(v);
            }
            path.push(v);
        }

        if (path.size() != g.E() + 1) {
            path = null;
        }
    }

    private int nonIsolatedVertex(Graph g) {
        for (int v = 0; v < g.V(); v++) {
            if (g.degree(v) > 0) {
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

    private static void unitTest(Graph G, String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);

        EulerianPath euler = new EulerianPath(G);

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
        Graph G1 = GraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");

        // Eulerian path
        Graph G2 = GraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");

        // add one random edge
        Graph G3 = new Graph(G2);
        G3.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G3, "one random edge added to Eulerian path");

        // self loop
        Graph G4 = new Graph(V);
        int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");

        // single edge
        Graph G5 = new Graph(V);
        G5.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G5, "single edge");

        // empty graph
        Graph G6 = new Graph(V);
        unitTest(G6, "empty graph");

        // random graph
        Graph G7 = GraphGenerator.simple(V, E);
        unitTest(G7, "simple graph");

    }

}
