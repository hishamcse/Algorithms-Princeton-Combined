package com.Hisham.Reduction;

import edu.princeton.cs.algs4.*;

public class BipartiteMatching {

    private final int V;                 // number of vertices in the graph
    private final BipartiteX bipartition;      // the bipartition
    private int cardinality;             // cardinality of current matching
    private final int[] mate;                  // mate[v] =  w if v-w is an edge in current matching
    private final boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
    private boolean[] marked;            // marked[v] = true iff v is reachable via alternating path
    private int[] edgeTo;                // edgeTo[v] = last edge on alternating path to v

    public BipartiteMatching(Graph g) {

        // check for bipartite
        bipartition = new BipartiteX(g);
        if (!bipartition.isBipartite()) {
            throw new IllegalArgumentException("graph is not bipartite");
        }

        V = g.V();
        mate = new int[V];
        for (int v = 0; v < V; v++) {
            mate[v] = -1;
        }

        while (hasAugmentingPath(g)) {
            // find one endpoint t in alternating path
            int t = -1;
            for (int v = 0; v < g.V(); v++) {
                if (!isMatched(v) && edgeTo[v] != -1) {
                    t = v;
                    break;
                }
            }

            // update the matching according to alternating path in edgeTo[] array
            for (int v = t; v != -1; v = edgeTo[edgeTo[v]]) {
                int w = edgeTo[v];
                mate[v] = w;
                mate[w] = v;
            }
            cardinality++;
        }

        inMinVertexCover = new boolean[V];
        for (int v = 0; v < V; v++) {
            if (bipartition.color(v) && !marked[v]) inMinVertexCover[v] = true;
            if (!bipartition.color(v) && marked[v]) inMinVertexCover[v] = true;
        }
    }

    private boolean hasAugmentingPath(Graph g) {
        marked = new boolean[V];
        edgeTo = new int[V];

        for (int v = 0; v < V; v++) {
            edgeTo[v] = -1;
        }

        Queue<Integer> queue = new Queue<>();
        for (int v = 0; v < V; v++) {
            if (bipartition.color(v) && !isMatched(v)) {
                queue.enqueue(v);
                marked[v] = true;
            }
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : g.adj(v)) {
                if (isResidualGraphedge(v, w) && !marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    if (!isMatched(w)) {
                        return true;
                    }
                    queue.enqueue(w);
                }
            }
        }
        return false;
    }

    private boolean isResidualGraphedge(int v, int w) {
        if (bipartition.color(v) && mate[v] != w) {
            return true;
        }
        if (!bipartition.color(v) && mate[v] == w) {
            return true;
        }
        return false;
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

        if (G.V() < 1000) StdOut.println(G);

        BipartiteMatching matching = new BipartiteMatching(G);

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
