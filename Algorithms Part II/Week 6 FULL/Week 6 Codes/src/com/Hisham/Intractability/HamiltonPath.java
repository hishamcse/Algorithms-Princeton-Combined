package com.Hisham.Intractability;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class HamiltonPath {

    private final boolean[] marked;     // vertices on current path
    private int count = 0;           // number of Hamiltonian paths

    public HamiltonPath(Graph G) {
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++)
            dfs(G, v, 1);
    }

    private void dfs(Graph G, int v, int depth) {

        marked[v] = true;
        if (depth == G.V()) {
            count++;
        }

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w, depth + 1);
            }
        }
        marked[v] = false;
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        Graph g = new Graph(in);
        HamiltonPath h = new HamiltonPath(g);
        System.out.println(h.count());
    }
}
