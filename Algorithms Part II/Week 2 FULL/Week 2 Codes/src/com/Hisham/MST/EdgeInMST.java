package com.Hisham.MST;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class EdgeInMST {

    /**
     * idea : https://github.com/yixuaz/algorithm4-princeton-cos226/blob/master/princetonSolution/src/part2/week2/mst/IsEdgeInMST.java
     * <p>
     * we have two solution.
     * 1. use union find to simulate the situation above.
     * 2. use dfs, start from one vertex from this edge,  then dfs, only choose the edge which weight smaller than this edge,
     * check dfs can achieve another vertex from this edge.
     * if can achieve, this edge should not in mst,or it will create a cycle.
     */

    private final boolean[] marked;
    private boolean inMST;

    public EdgeInMST(EdgeWeightedGraph g, Edge e) {
        marked = new boolean[g.V()];
        inMST = false;
        int v = e.either();
        int w = e.other(v);
        int c = 0;

        // first check whether the edge really exists in the graph g,if not there is no chance to
        // have this edge in mst.and also as it is undirected edgeWeighted graph,so we have to
        // be careful about the reverse of the edge also
        for (Edge s : g.edges()) {
            if ((s.either() == v || s.either() == w) && (s.other(s.either()) == w || s.other(s.either()) == v)
                    && s.weight() == e.weight()) {
                c = 1;
                break;
            }
        }

        if (c == 1) {
            inMST = solveUF(g, e);
//            inMST = solveDFS(g, v, w, e.weight());
        } else {
            inMST = false;
        }
    }

    // perfect solution
    private boolean solveUF(EdgeWeightedGraph g, Edge e) {
        UF uf = new UF(g.V());
        for (Edge s : g.edges()) {
            if (Double.compare(s.weight(), e.weight()) < 0) {
                uf.union(s.either(), s.other(s.either()));
            }
        }
        return uf.find(e.either()) != uf.find(e.other(e.either()));
    }

    private boolean solveDFS(EdgeWeightedGraph g, int v, int t, double weight) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);
            if (marked[v] || Double.compare(e.weight(), weight) >= 0) {
                continue;
            }
            if (w == t) {
                return false;
            }
            if (!solveDFS(g, w, t, weight)) {
                return false;
            }
        }
        return true;
    }

    public boolean inMST() {
        return inMST;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        EdgeInMST inMST = new EdgeInMST(G, new Edge(1, 3, .29));
        System.out.println(inMST.inMST());
    }
}
