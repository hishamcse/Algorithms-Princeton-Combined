package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;

public class MonotonicSP {

    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    public MonotonicSP(EdgeWeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MAX_VALUE;
        }
        distTo[s] = 0.0;

        // ascending
        MinPQ<DirectedEdge> pq=new MinPQ<>((a,b) -> Double.compare(a.weight(),b.weight()));
        for(DirectedEdge e:g.edges()){
            pq.insert(e);
        }

        while (!pq.isEmpty()){
            DirectedEdge e=pq.delMin();
            relax(e);
        }

        // descending
        pq=new MinPQ<>((a,b) -> Double.compare(b.weight(),a.weight()));
        for(DirectedEdge e:g.edges()){
            pq.insert(e);
        }

        while (!pq.isEmpty()){
            DirectedEdge e=pq.delMin();
            relax(e);
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.MAX_VALUE;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if(!hasPathTo(v)){
            return null;
        }
        Stack<DirectedEdge> stack = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            stack.push(e);
        }
        return stack;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(StdIn.readString());

        // compute shortest paths
        MonotonicSP sp = new MonotonicSP(G, s);

        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }
}
