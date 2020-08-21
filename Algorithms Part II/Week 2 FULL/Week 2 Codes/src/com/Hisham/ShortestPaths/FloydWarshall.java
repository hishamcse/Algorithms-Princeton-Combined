package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDirectedCycle;

public class FloydWarshall {

    private boolean hasNegativeCycle;
    private final DirectedEdge[][] edgeTo;
    private final double[][] distTo;

    public FloydWarshall(AdjMatrixEdgeWeightedDigraph g) {
        edgeTo = new DirectedEdge[g.V()][g.V()];
        distTo = new double[g.V()][g.V()];

        for (int i = 0; i < g.V(); i++) {
            for (int j = 0; j < g.V(); j++) {
                distTo[i][j] = Double.MAX_VALUE;
            }
        }

        for (int v = 0; v < g.V(); v++) {
            for (DirectedEdge e : g.adj(v)) {
                distTo[e.from()][e.to()] = e.weight();
                edgeTo[e.from()][e.to()] = e;
            }
            //self loops
            if (distTo[v][v] >= 0) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        for (int i = 0; i < g.V(); i++) {
            for (int v = 0; v < g.V(); v++) {
                if (edgeTo[v][i] == null) {
                    continue;
                }
                for (int w = 0; w < g.V(); w++) {
                    if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                if (distTo[v][v] < 0.0) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
    }

    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        for (int v = 0; v < distTo.length; v++) {
            if (distTo[v][v] < 0.0) {
                int V = edgeTo.length;
                EdgeWeightedDigraph sp = new EdgeWeightedDigraph(V);
                for (int w = 0; w < V; w++) {
                    if (edgeTo[v][w] != null) {
                        sp.addEdge(edgeTo[v][w]);
                    }
                }
                EdgeWeightedDirectedCycle f = new EdgeWeightedDirectedCycle(sp);
                if (f.hasCycle()) {
                    return f.cycle();
                }
            }
        }
        return null;
    }

    public boolean hasPath(int s, int t) {
        return distTo[s][t] < Double.MAX_VALUE;
    }

    public double dist(int s, int t) {
        return distTo[s][t];
    }

    public Iterable<DirectedEdge> path(int s, int t) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPath(s, t)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[s][t]; e != null; e = edgeTo[s][e.from()]) {
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args) {

        // random graph with V vertices and E edges, parallel edges allowed
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V);
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * (StdRandom.uniform() - 0.15)) / 100.0;
            if (v == w) G.addEdge(new DirectedEdge(v, w, Math.abs(weight)));
            else G.addEdge(new DirectedEdge(v, w, weight));
        }

        StdOut.println(G);

        // run Floyd-Warshall algorithm
        FloydWarshall spt = new FloydWarshall(G);

        // print all-pairs shortest path distances
        StdOut.printf("  ");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%6d ", v);
        }
        StdOut.println();
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (spt.hasPath(v, w)) StdOut.printf("%6.2f ", spt.dist(v, w));
                else StdOut.printf("  Inf ");
            }
            StdOut.println();
        }

        // print negative cycle
        if (spt.hasNegativeCycle()) {
            StdOut.println("Negative cost cycle:");
            for (DirectedEdge e : spt.negativeCycle())
                StdOut.println(e);
            StdOut.println();
        }

        // print all-pairs shortest paths
        else {
            for (int v = 0; v < G.V(); v++) {
                for (int w = 0; w < G.V(); w++) {
                    if (spt.hasPath(v, w)) {
                        StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                        for (DirectedEdge e : spt.path(v, w))
                            StdOut.print(e + "  ");
                        StdOut.println();
                    } else {
                        StdOut.printf("%d to %d no path\n", v, w);
                    }
                }
            }
        }

    }
}
