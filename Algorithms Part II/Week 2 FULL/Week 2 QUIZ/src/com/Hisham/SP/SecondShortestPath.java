package com.Hisham.SP;

import edu.princeton.cs.algs4.*;

import java.util.LinkedList;
import java.util.List;

public class SecondShortestPath {
    List<DirectedEdge> result;

    public SecondShortestPath(EdgeWeightedDigraph g, int s, int t) {
        DijkstraSP sp = new DijkstraSP(g, s);
        Iterable<DirectedEdge> iterS = sp.pathTo(t);
        List<DirectedEdge> list = new LinkedList<>();
        for (DirectedEdge e : iterS) {
            list.add(e);
        }

        // reversing the graph
        EdgeWeightedDigraph reverse = new EdgeWeightedDigraph(g.V());
        for (int v = g.V() - 1; v >= 0; v--) {
            for (DirectedEdge w : g.adj(v)) {
                reverse.addEdge(w);
            }
        }

        DijkstraSP sp2 = new DijkstraSP(reverse, t);
        double min = Double.MAX_VALUE;
        DirectedEdge edge = null;
        for (DirectedEdge e : g.edges()) {
            if (list.contains(e)) {
                continue;
            }
            double dist = sp.distTo(e.from()) + sp2.distTo(e.to()) + e.weight();
            if (dist < min) {
                min = dist;
                edge = e;
            }
        }

        result = new LinkedList<>();
        if (edge == null) {
            result = null;
            return;
        }
        for (DirectedEdge e : sp.pathTo(edge.from())) {
            result.add(e);
        }
        result.add(edge);
        for (DirectedEdge e : sp2.pathTo(edge.to())) {
            result.add(new DirectedEdge(e.to(), e.from(), e.weight()));
        }
    }

    public Iterable<DirectedEdge> path() {
        return result;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(StdIn.readString());

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, s);

        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }

        in = new In(StdIn.readString());
        G = new EdgeWeightedDigraph(in);
        s = Integer.parseInt(StdIn.readString());
        int t = Integer.parseInt(StdIn.readString());

        // compute shortest paths
        SecondShortestPath sp2 = new SecondShortestPath(G, s, t);

        // print shortest path
        if (sp2.path() != null) {
            StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
            for (DirectedEdge e : sp2.path()) {
                StdOut.print(e + "   ");
            }
            StdOut.println();
        } else {
            System.out.println("no such path exists");
        }
    }
}
