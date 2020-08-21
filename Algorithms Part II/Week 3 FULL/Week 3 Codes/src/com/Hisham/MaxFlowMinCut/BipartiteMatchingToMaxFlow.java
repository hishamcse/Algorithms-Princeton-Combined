package com.Hisham.MaxFlowMinCut;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

public class BipartiteMatchingToMaxFlow {

    private final int V;
    private final int[] mate;
    private final int cardinality;
    private final boolean[] inMinVertexCover;

    public BipartiteMatchingToMaxFlow(Graph g) {

        // check for bipartite
        BipartiteX bipartiteX = new BipartiteX(g);
        if (!bipartiteX.isBipartite()) {
            throw new IllegalArgumentException("graph is not bipartite");
        }

        V = g.V();
        int s = V;          // source
        int t = V + 1;      // target

        // flowNetwork creation
        FlowNetwork flowNetwork = new FlowNetwork(V + 2);
        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v)) {
                if (bipartiteX.color(v)) {
                    flowNetwork.addEdge(new FlowEdge(v, w, Double.POSITIVE_INFINITY));
                } else {
                    flowNetwork.addEdge(new FlowEdge(w, v, Double.POSITIVE_INFINITY));
                }
            }
        }

        for (int v = 0; v < g.V(); v++) {
            if (bipartiteX.color(v)) {
                flowNetwork.addEdge(new FlowEdge(s, v, 1));
            } else {
                flowNetwork.addEdge(new FlowEdge(v, t, 1));
            }
        }

        // maxFlow algorithm
        FordFulkerson maxFlow = new FordFulkerson(flowNetwork, s, t);
        cardinality = (int) maxFlow.value();

        // finding the values of mate
        mate = new int[V];
        for (int v = 0; v < g.V(); v++) {
            mate[v] = -1;
            for (FlowEdge e : flowNetwork.adj(v)) {
                if (e.from() == v && e.flow() > 0 && e.to() != t) {
                    int w = e.other(v);
                    mate[v] = w;
                    mate[w] = v;
                }
            }
        }

        // whether in min vertex cover or not
        inMinVertexCover = new boolean[V];
        for (int v = 0; v < g.V(); v++) {
            if (bipartiteX.color(v) && !maxFlow.inCut(v)) {
                inMinVertexCover[v] = true;
            }
            if (!bipartiteX.color(v) && maxFlow.inCut(v)) {
                inMinVertexCover[v] = true;
            }
        }
    }

    public int mate(int v) {
        return mate[v];
    }

    public boolean isMatched(int v) {
        return mate[v] != -1;
    }

    public int size() {
        return cardinality;
    }

    public boolean isPerfect() {
        return cardinality * 2 == V;
    }

    public boolean inMinVertexCover(int v) {
        return inMinVertexCover[v];
    }

    public static void main(String[] args) {
        int V1 = Integer.parseInt(StdIn.readLine());
        int V2 = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        Graph G = GraphGenerator.bipartite(V1, V2, E);
        StdOut.println(G);

        BipartiteMatchingToMaxFlow matching = new BipartiteMatchingToMaxFlow(G);

        // print maximum matching
        StdOut.printf("Number of edges in max matching        = %d\n", matching.size());
        StdOut.printf("Number of vertices in min vertex cover = %d\n", matching.size());
        StdOut.printf("Graph has a perfect matching           = %b\n", matching.isPerfect());
        StdOut.println();

        if (G.V() >= 1000) return;

        StdOut.print("Max matching: ");
        for (int v = 0; v < G.V(); v++) {
            int w = matching.mate(v);
            if (matching.isMatched(v) && v < w)  // print each edge only once
                StdOut.print(v + "-" + w + " ");
        }
        StdOut.println();

        // print minimum vertex cover
        StdOut.print("Min vertex cover: ");
        for (int v = 0; v < G.V(); v++)
            if (matching.inMinVertexCover(v))
                StdOut.print(v + " ");
        StdOut.println();
    }
}
