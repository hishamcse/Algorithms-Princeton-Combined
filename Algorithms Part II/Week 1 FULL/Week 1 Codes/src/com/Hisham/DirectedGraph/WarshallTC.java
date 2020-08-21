package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WarshallTC {

    private final boolean[][] tc;

    public WarshallTC(AdjMatrixDiGraph g) {
        int V = g.V();
        tc = new boolean[V][V];
        for (int v = 0; v < V; v++) {
            for (int w : g.adj(v)) {
                tc[v][w] = true;
            }
            tc[v][v] = true;
        }

        //warshall algorithm
        for (int i = 0; i < V; i++) {
            for (int v = 0; v < V; v++) {
                if (!tc[v][i]) continue;
                for (int w = 0; w < V; w++) {
                    if (tc[v][i] && tc[i][w]) {
                        tc[v][w] = true;
                    }
                }
            }
        }
    }

    public boolean hasPath(int v, int w) {
        return tc[v][w];
    }

    public static void main(String[] args) {
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        AdjMatrixDiGraph G = new AdjMatrixDiGraph(V, E);
        StdOut.println(G);
        WarshallTC tc = new WarshallTC(G);

        // print header
        StdOut.println("Transitive closure");
        StdOut.println("-----------------------------------");
        StdOut.print("     ");
        for (int v = 0; v < G.V(); v++)
            StdOut.printf("%3d", v);
        StdOut.println();

        // print transitive closure
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (tc.hasPath(v, w)) StdOut.printf("  x");
                else StdOut.printf("   ");
            }
            StdOut.println();
        }
    }
}
