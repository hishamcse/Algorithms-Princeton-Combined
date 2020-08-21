package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;

public class CPM {

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());

        int n = Integer.parseInt(in.readLine());
        int source = 2 * n;
        int sink = 2 * n + 1;

        EdgeWeightedDigraph g = new EdgeWeightedDigraph(2 * n + 2);
        for (int i = 0; i < n; i++) {
            String[] s = in.readLine().split(" ");
            double duration = Double.parseDouble(s[0]);
            g.addEdge(new DirectedEdge(source, i, 0.0));
            g.addEdge(new DirectedEdge(i, i + n, duration));
            g.addEdge(new DirectedEdge(i + n, sink, 0.0));

            int m = Integer.parseInt(s[1]);
            int k = 2;
            for (int j = 0; j < m; j++) {
                int precedence = Integer.parseInt(s[k + j]);
                g.addEdge(new DirectedEdge(i + n, precedence, 0.0));
            }
        }

        AcyclicLP lp = new AcyclicLP(g, source);

        StdOut.println(" job   start  finish");
        StdOut.println("--------------------");
        for (int i = 0; i < n; i++) {
            StdOut.printf("%4d %7.1f %7.1f\n", i, lp.distTo(i), lp.distTo(i + n));
        }
        StdOut.printf("Finish time: %7.1f\n", lp.distTo(sink));
    }
}
