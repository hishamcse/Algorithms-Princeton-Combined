package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.GraphGenerator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Bridge {

    // low[v] indicates earliest visited vertex reachable from subtree rooted with v
    // pre[v] indicates the discovered time of visit for vertex v

    private final int[] low;
    private final int[] pre;
    private int cnt;
    private int bridges;

    public Bridge(Graph g) {
        low = new int[g.V()];
        pre = new int[g.V()];
        cnt = 0;
        bridges = 0;
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
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : g.adj(v)) {
            if (pre[w] == -1) {
                dfs(g, v, w);

                low[v] = Math.min(low[v], low[w]);

                if (low[w] == pre[w]) {
                    bridges++;
                }
                //or
//                if(low[w]>pre[v]){
//                    bridges++;
//                }

            } else if (w != u) {
                low[v] = Math.min(low[v], pre[w]);
            }
        }
    }

    public int components() {
        return bridges + 1;
    }

    public static void main(String[] args) {
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        Graph G = GraphGenerator.simple(V, E);
        StdOut.println(G);

        Bridge bridge = new Bridge(G);
        StdOut.println("Edge connected components = " + bridge.components());
    }
}
