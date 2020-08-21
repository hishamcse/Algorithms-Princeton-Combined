package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public class EdgeWeightedDiGraph {

    private final int V;
    private int E;
    private final Bag<DirectedEdge>[] adj;
    private final int[] indegree;

    public EdgeWeightedDiGraph(int V) {
        this.V = V;
        this.E = 0;
        indegree=new int[V];
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedDiGraph(int V, int E) {
        this(V);
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
        }
    }

    public EdgeWeightedDiGraph(In in) {
        try {
            this.V = in.readInt();
            this.adj = (Bag<DirectedEdge>[]) new Bag[this.V];
            indegree=new int[this.V];
            for (int i = 0; i < this.V; i++) {
                this.adj[i] = new Bag<>();
            }
            int E = in.readInt();
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                double weight = in.readDouble();
                addEdge(new DirectedEdge(v, w, weight));
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

    public EdgeWeightedDiGraph(EdgeWeightedDiGraph g) {
        this.V = g.V();
        this.E = g.E();
        this.adj = (Bag<DirectedEdge>[]) new Bag[this.V];
        indegree=new int[this.V];

        for (int i = 0; i < this.V(); i++) {
            adj[i] = new Bag<>();
        }

        for(int i=0;i<this.V();i++){
            indegree[i]=g.indegree(i);
        }

        for (int v = 0; v < g.V(); v++) {
            // as we want the exact order to be followed as the parameter graph.
            // so,reverse.because,Bag adds item at the first

            Stack<DirectedEdge> stack = new Stack<>();
            for (DirectedEdge w : g.adj(v)) {
                stack.push(w);
            }
            for (DirectedEdge w : stack) {
                adj[v].add(w);
            }
        }
    }

    public void addEdge(DirectedEdge e) {
        E++;
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        indegree[w]++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public int outdegree(int v) {
        return adj[v].size();
    }

    public int indegree(int v) {
        return indegree[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> edges = new Bag<>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (DirectedEdge e : adj(v)) {
                if (e.to() > v) {
                    edges.add(e);
                } else if (e.to() == v) {
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
            for (DirectedEdge w : adj[v]) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(in);
        StdOut.println(G);
    }
}
