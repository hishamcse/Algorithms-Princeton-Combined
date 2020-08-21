package com.Hisham.MaxFlowMinCut;

import edu.princeton.cs.algs4.*;

import java.util.Arrays;

public class GlobalMinCut {   // stoer-wagner algorithm
    // the weight of the minimum cut
    private double weight = Double.POSITIVE_INFINITY;

    // cut[v] = true if v is on the first subset of vertices of the minimum cut;
    // or false if v is on the second subset
    private boolean[] cut;

    // number of vertices
    private int V;

    private static class CutPhase {
        private double weight; // the weight of the minimum s-t cut
        private int s;         // the vertex s
        private int t;         // the vertex t

        public CutPhase(double weight, int s, int t) {
            this.weight = weight;
            this.s = s;
            this.t = t;
        }
    }

    public GlobalMinCut(EdgeWeightedGraph G) {
        V = G.V();
        minCut(G, 0);
    }

    private void makeCut(int t, UF uf) {
        for (int v = 0; v < cut.length; v++) {
            cut[v] = (uf.find(v) == uf.find(t));
        }
    }

    private void minCut(EdgeWeightedGraph G, int a) {
        UF uf = new UF(G.V());
        boolean[] marked = new boolean[G.V()];
        cut = new boolean[G.V()];
        CutPhase cp = new CutPhase(0.0, a, a);
        for (int v = G.V(); v > 1; v--) {
            cp = minCutPhase(G, marked, cp);
            if (cp.weight < weight) {
                weight = cp.weight;
                makeCut(cp.t, uf);
            }
            G = contractEdge(G, cp.s, cp.t);
            marked[cp.t] = true;
            uf.union(cp.s, cp.t);
        }
    }

    private CutPhase minCutPhase(EdgeWeightedGraph G, boolean[] marked, CutPhase cp) {
        IndexMaxPQ<Double> pq = new IndexMaxPQ<>(G.V());
        for (int v = 0; v < G.V(); v++) {
            if (v != cp.s && !marked[v]) pq.insert(v, 0.0);
        }
        pq.insert(cp.s, Double.POSITIVE_INFINITY);
        while (!pq.isEmpty()) {
            int v = pq.delMax();
            cp.s = cp.t;
            cp.t = v;
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (pq.contains(w)) pq.increaseKey(w, pq.keyOf(w) + e.weight());
            }
        }
        cp.weight = 0.0;
        for (Edge e : G.adj(cp.t)) {
            cp.weight += e.weight();
        }
        return cp;
    }

    private EdgeWeightedGraph contractEdge(EdgeWeightedGraph G, int s, int t) {
        EdgeWeightedGraph H = new EdgeWeightedGraph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (v == s && w == t || v == t && w == s) continue;
                if (v < w) {
                    if (w == t) H.addEdge(new Edge(v, s, e.weight()));
                    else if (v == t) H.addEdge(new Edge(w, s, e.weight()));
                    else H.addEdge(new Edge(v, w, e.weight()));
                }
            }
        }
        return H;
    }


    public double weight() {
        return weight;
    }

    public boolean cut(int v) {
        return cut[v];
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        GlobalMincut mc = new GlobalMincut(G);
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (mc.cut(v)) StdOut.print(v + " ");
        }
        StdOut.println();
        StdOut.println("Min cut weight = " + mc.weight());
    }
}
