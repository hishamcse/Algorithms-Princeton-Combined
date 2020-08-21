package com.Hisham.MaxFlowMinCut;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;

public class FordFulkerson {    // using shortest path

    private int V;
    private FlowEdge[] edgeTo;
    private boolean[] marked;
    private double value;

    public FordFulkerson(FlowNetwork g, int s, int t) {
        V = g.V();
        value = excess(g,t);

        while (hasAugmentingPath(g, s, t)) {

            double bottle = Double.MAX_VALUE;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }
            value += bottle;
        }

    }

    private boolean hasAugmentingPath(FlowNetwork g, int s, int t) {
        edgeTo = new FlowEdge[g.V()];
        marked = new boolean[g.V()];

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;

        while (!queue.isEmpty() && !marked[t]) {
            int v = queue.dequeue();
            for (FlowEdge e : g.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = e;
                    queue.enqueue(w);
                }
            }
        }
        return marked[t];
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    public double excess(FlowNetwork g,int v){
        double excess=0.0;
        for(FlowEdge e:g.adj(v)){
            if(v==e.from()){
                excess-=e.flow();
            }else{
                excess+=e.flow();
            }
        }
        return excess;
    }

    public static void main(String[] args) {

        // create flow network with V vertices and E edges
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        int s = 0, t = V-1;
        FlowNetwork G = new FlowNetwork(V, E);
        StdOut.println(G);

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(G, s, t);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("   " + e);
            }
        }

        // print min-cut
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (maxflow.inCut(v)) StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.println("Max flow value = " +  maxflow.value());
    }

}
