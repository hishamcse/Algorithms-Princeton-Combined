package com.Hisham.MaxFlowMinCut;

import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public class FlowNetwork {

    private final int V;
    private int E;
    private final Bag<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public FlowNetwork(int V, int E) {
        this(V);
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double capacity = StdRandom.uniform(100);
            FlowEdge e = new FlowEdge(v, w, capacity);
            addEdge(e);
        }
    }

    public FlowNetwork(In in) {
        try {
            this.V = in.readInt();
            this.adj = (Bag<FlowEdge>[]) new Bag[this.V];
            for (int i = 0; i < this.V; i++) {
                this.adj[i] = new Bag<>();
            }
            int E = in.readInt();
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                double capacity = in.readDouble();
                addEdge(new FlowEdge(v, w, capacity));
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

    public void addEdge(FlowEdge e) {
        E++;
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    public int degree(int v) {
        return adj[v].size();
    }

    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> edges = new Bag<>();
        for (int v = 0; v < V; v++) {
            for (FlowEdge e : adj(v)) {
                if (e.to() != v) {
                    edges.add(e);
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
            for (FlowEdge w : adj[v]) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        FlowNetwork G = new FlowNetwork(in);
        StdOut.println(G);
    }

}
