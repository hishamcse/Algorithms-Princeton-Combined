package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public class DiGraph {

    private final int V;
    private int E;
    private final Bag<Integer>[] adj;
    private final int[] indegree;

    public DiGraph(int V) {
        this.V = V;
        this.E = 0;
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public DiGraph(In in) {
        try {
            this.V = in.readInt();
            this.adj = (Bag<Integer>[]) new Bag[this.V];
            this.indegree = new int[this.V];
            for (int i = 0; i < this.V; i++) {
                this.adj[i] = new Bag<>();
            }
            int E = in.readInt();
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

    public DiGraph(DiGraph g) {
        this.V = g.V();
        this.E = g.E();
        this.adj = (Bag<Integer>[]) new Bag[this.V];
        this.indegree = new int[this.V];

        for (int i = 0; i < this.V; i++) {
            indegree[i] = g.indegree[i];
        }

        for (int i = 0; i < this.V(); i++) {
            adj[i] = new Bag<>();
        }

        for (int v = 0; v < g.V(); v++) {
            //as we want the exact order to be followed as the parameter graph.
            // so,reverse.because,Bag adds item at the first

            Stack<Integer> stack = new Stack<>();
            for (Integer w : g.adj(v)) {
                stack.push(w);
            }
            for (Integer w : stack) {
                adj[v].add(w);
            }
        }
    }

    public void addEdge(int v, int w) {     // v -> w
        E++;
        adj[v].add(w);
        indegree[w]++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int outdegree(int v) {
        return adj[v].size();
    }

    public int indegree(int v) {
        return indegree[v];
    }

    public DiGraph reverse() {
        DiGraph reverse = new DiGraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" vertices, ").append(E).append(" edges ").append("\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (int w : adj[v]) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        DiGraph G = new DiGraph(in);
        StdOut.println(G);

        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                StdOut.println(v + " -> " + w);
            }
        }
    }

}
