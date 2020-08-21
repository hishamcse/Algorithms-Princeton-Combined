package com.Hisham.MST;

import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public class EdgeWeightedGraph {

    private final int V;
    private int E;
    private final Bag<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedGraph(int V, int E) {
        this(V);
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    public EdgeWeightedGraph(In in) {
        try {
            this.V = in.readInt();
            this.adj = (Bag<Edge>[]) new Bag[this.V];
            for (int i = 0; i < this.V; i++) {
                this.adj[i] = new Bag<>();
            }
            int E = in.readInt();
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                double weight = in.readDouble();
                addEdge(new Edge(v, w, weight));
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

    public EdgeWeightedGraph(EdgeWeightedGraph g) {
        this.V = g.V();
        this.E = g.E();
        this.adj = (Bag<Edge>[]) new Bag[this.V];

        for (int i = 0; i < this.V(); i++) {
            adj[i] = new Bag<>();
        }

        for (int v = 0; v < g.V(); v++) {
            // as we want the exact order to be followed as the parameter graph.
            // so,reverse.because,Bag adds item at the first

            Stack<Edge> stack = new Stack<>();
            for (Edge w : g.adj(v)) {
                stack.push(w);
            }
            for (Edge w : stack) {
                adj[v].add(w);
            }
        }
    }

    public void addEdge(Edge e) {
        E++;
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public int degree(int v) {
        return adj[v].size();
    }

    public Iterable<Edge> edges() {
        Bag<Edge> edges = new Bag<>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    edges.add(e);
                } else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) {
                        edges.add(e);
                    }
                    selfLoops++;
                }
            }
        }
        return edges;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" vertices, ").append(E).append(" edges ").append("\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (Edge w : adj[v]) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        StdOut.println(G);
    }

}
