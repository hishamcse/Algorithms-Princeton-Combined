package com.Hisham.MST;

import edu.princeton.cs.algs4.*;

import java.util.LinkedList;
import java.util.List;

public class MinWeightEdgeFeedbackPrim {

    private final boolean[] marked;
    private final Edge[] edgeTo;
    private final double[] distTo;
    private final IndexMaxPQ<Double> maxPQ;
    private final EdgeWeightedGraph G;

    public MinWeightEdgeFeedbackPrim(EdgeWeightedGraph g) {
        marked = new boolean[g.V()];
        edgeTo = new Edge[g.V()];
        distTo = new double[g.V()];
        maxPQ = new IndexMaxPQ<>(g.V());
        G=g;

        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.MIN_VALUE;
        }
        prim(g);
    }

    private void prim(EdgeWeightedGraph g) {
        distTo[0] = 0.0;
        maxPQ.insert(0, distTo[0]);
        while (!maxPQ.isEmpty()) {
            int v = maxPQ.delMax();
            visit(g, v);
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);
            if (marked[w]) {
                continue;
            }
            if (e.weight() > distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (maxPQ.contains(w)) {
                    maxPQ.increaseKey(w, distTo[w]);
                } else {
                    maxPQ.insert(w, distTo[w]);
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        List<Edge> mst = new LinkedList<>();
        for (Edge e : edgeTo) {
            if (e != null) {
                mst.add(e);
            }
        }
        Queue<Edge> res=new Queue<>();
        for(Edge e:G.edges()){
            if(!mst.contains(e)){
                res.enqueue(e);
            }
        }
        return res;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges()) {
            weight += e.weight();
        }
        return weight;
    }

    public int size(){
        int c=0;
        for(Edge ignored :edges()){
            c++;
        }
        return c;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        MinWeightEdgeFeedbackPrim mst = new MinWeightEdgeFeedbackPrim(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
        System.out.println("size = "+mst.size());
    }
}
