package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;

public class Topological {

    private Iterable<Integer> order;
    private int[] rank;

    public Topological(Digraph g) {
        DirectedCycle finder = new DirectedCycle(g);
        if (!finder.hasCycle()) {   // as topological ordering doesnt support cycles
            DepthFirstOrder d = new DepthFirstOrder(g);
            order = d.reversePost();
            rank = new int[g.V()];
            int i = 0;
            for (int v : order) {
                rank[v] = i++;
            }
        }
    }

    public Topological(EdgeWeightedDigraph G) {
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    public boolean hasOrder() {
        return order != null;
    }

    public Iterable<Integer> order() {
        return order;
    }

    public int rank(int v) {
        if (hasOrder()) {
            return rank[v];
        }
        return -1;
    }

    public static void main(String[] args) {

        //test 1
        String filename = StdIn.readLine();
        String delimiter = StdIn.readLine();
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Topological topological = new Topological(sg.digraph());
        for (int v : topological.order()) {
            StdOut.println(sg.nameOf(v));
        }

        //test 2
        In in = new In(StdIn.readLine());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        Topological t = new Topological(G);
        for (int v : t.order()) {
            System.out.println(v + " : " + v);
        }
    }

}
