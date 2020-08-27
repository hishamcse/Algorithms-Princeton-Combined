package com.Hisham.Reduction;

import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class HopcroftKarp {

    private final int V;                    // number of vertices in the graph
    private final BipartiteX bipartition;      // the bipartition
    private int cardinality;               // cardinality of current matching
    private final int[] mate;                  // mate[v] =  w if v-w is an edge in current matching
    private final boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
    private boolean[] marked;            // marked[v] = true iff v is reachable via alternating path
    private int[] distTo;                // edgeTo[v] = last edge on alternating path to v

    public HopcroftKarp(Graph g) {

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

            // to be able to iterate over each adjacency list, keeping track of which
            // vertex in each adjacency list needs to be explored next
            Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[g.V()];
            for (int v = 0; v < g.V(); v++) {
                adj[v] = g.adj(v).iterator();
            }

            // for each unmatched vertex s on one side of bipartition
            for (int s = 0; s < V; s++) {
                if (isMatched(s) || !bipartition.color(s)) continue;   // or use distTo[s] == 0

                // find augmenting path from s using nonRecursive DFS
                Stack<Integer> path = new Stack<>();
                path.push(s);
                while (!path.isEmpty()) {
                    int v = path.peek();

                    // retreat, no more edges in level graph leaving v
                    if (!adj[v].hasNext()) {
                        path.pop();
                    }

                    // advance
                    else {
                        // process edge v-w only if it is an edge in level graph
                        int w = adj[v].next();
                        if (!isLevelGraphEdge(v, w)) continue;

                        // add w to augmenting path
                        path.push(w);

                        // augmenting path found: update the matching
                        if (!isMatched(w)) {
                            StdOut.println("augmenting path: " + toString(path));

                            while (!path.isEmpty()) {
                                int x = path.pop();
                                int y = path.pop();
                                mate[x] = y;
                                mate[y] = x;
                            }
                            cardinality++;
                        }
                    }
                }
            }
        }

        inMinVertexCover = new boolean[V];
        for (int v = 0; v < V; v++) {
            if (bipartition.color(v) && !marked[v]) inMinVertexCover[v] = true;
            if (!bipartition.color(v) && marked[v]) inMinVertexCover[v] = true;
        }
    }

    private boolean hasAugmentingPath(Graph g) {
        marked = new boolean[V];
        distTo = new int[V];

        for (int v = 0; v < V; v++) {
            distTo[v] = Integer.MAX_VALUE;
        }

        Queue<Integer> queue = new Queue<>();
        for (int v = 0; v < V; v++) {
            if (bipartition.color(v) && !isMatched(v)) {
                queue.enqueue(v);
                marked[v] = true;
                distTo[v] = 0;
            }
        }

        boolean hasAugmentingPath = false;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : g.adj(v)) {
                if (isResidualGraphedge(v, w) && !marked[w]) {
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    if (!isMatched(w)) {
                        hasAugmentingPath = true;
                    }
                    // stop enqueuing vertices once an alternating path has been discovered
                    // (no vertex on same side will be marked if its shortest path distance longer)
                    if (!hasAugmentingPath) {
                        queue.enqueue(w);
                    }
                }
            }
        }
        return hasAugmentingPath;
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

    // is the edge v-w in the level graph?
    private boolean isLevelGraphEdge(int v, int w) {
        return (distTo[w] == distTo[v] + 1) && isResidualGraphedge(v, w);
    }

    private static String toString(Iterable<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int v : path)
            sb.append(v).append("-");
        String s = sb.toString();
        s = s.substring(0, s.lastIndexOf('-'));
        return s;
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

        HopcroftKarp matching = new HopcroftKarp(G);

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
