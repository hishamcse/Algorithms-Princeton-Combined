package com.Hisham.ShortestPaths;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Arbitrage {

    public static void main(String[] args) {
        int n = StdIn.readInt();
        String[] name = new String[n];

        EdgeWeightedDigraph g = new EdgeWeightedDigraph(n);
        for (int i = 0; i < n; i++) {
            name[i] = StdIn.readString();
            for (int j = 0; j < n; j++) {
                double cur = StdIn.readDouble();
                g.addEdge(new DirectedEdge(i, j, -Math.log(cur)));
            }
        }

        BellmanFordSP sp = new BellmanFordSP(g, 0);
        if (sp.hasNegativeCycle()) {
            double stake = 1000.0;
            for (DirectedEdge e : sp.negativeCycle()) {
                StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf(" = %10.5f %s ", stake, name[e.to()]);
                System.out.println();
            }
        } else {
            System.out.println("no arbitrage opportunity");
        }
    }
}
