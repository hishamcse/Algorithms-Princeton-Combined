package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DepthFirstOrder;

public class KosarajuSharirSCC {

    private final boolean[] marked;
    private final int[] id;     // the same id indicates same strongly connected component
    private int count;          // number of strongly connected components

    public KosarajuSharirSCC(Digraph g) {
        count = 0;
        marked = new boolean[g.V()];
        id = new int[g.V()];
        DepthFirstOrder dfs = new DepthFirstOrder(g.reverse());
        for (int v : dfs.reversePost()) {
            if (!marked[v]) {
                dfs(g, v);
                count++;
            }
        }
    }

    private void dfs(Digraph g, int v) {
        id[v] = count;
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return id[v];
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        Digraph G = new Digraph(in);
        KosarajuSharirSCC scc = new KosarajuSharirSCC(G);

        // number of connected components
        int m = scc.count();
        StdOut.println(m + " strong components");

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

    }
}
