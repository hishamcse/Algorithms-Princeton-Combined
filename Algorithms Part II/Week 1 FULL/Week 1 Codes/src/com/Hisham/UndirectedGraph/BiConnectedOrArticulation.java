package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.GraphGenerator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BiConnectedOrArticulation {   // Tarjan Algorithm (BiConnected.java)

    // see details at geeksforgeeks and BiConnected.java at booksite

    // low[v] indicates earliest visited vertex reachable from subtree rooted with v
    // pre[v] indicates the discovered time of visit for vertex v

    private final int[] low;
    private final int[] pre;
    private int cnt;
    private final boolean[] articulation;

    public BiConnectedOrArticulation(Graph g) {
        low = new int[g.V()];
        pre = new int[g.V()];
        articulation = new boolean[g.V()];
        cnt = 0;
        for (int v = 0; v < g.V(); v++) {
            low[v] = -1;
            pre[v] = -1;
        }
        for (int v = 0; v < g.V(); v++) {
            if (pre[v] == -1) {
                dfs(g, v, v);
            }
        }
    }

    private void dfs(Graph g, int u, int v) {
        int children = 0;
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : g.adj(v)) {
            if (pre[w] == -1) {
                children++;
                dfs(g, v, w);

                low[v] = Math.min(low[v], low[w]);

                if (low[w] >= pre[v] && u != v) {
                    articulation[v] = true;
                }

            } else if (w != u) {
                low[v] = Math.min(low[v], pre[w]);
            }
        }
        if (u == v && children > 1) {
            articulation[v] = true;
        }
    }

    public boolean isArticulation(int v) {
        return articulation[v];
    }

    public static void main(String[] args) {
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        Graph G = GraphGenerator.simple(V, E);
        StdOut.println(G);

        BiConnectedOrArticulation bic = new BiConnectedOrArticulation(G);

        // print out articulation points
        StdOut.println();
        StdOut.println("Articulation points");
        StdOut.println("-------------------");
        for (int v = 0; v < G.V(); v++)
            if (bic.isArticulation(v)) StdOut.println(v);
    }

}
