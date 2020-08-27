package com.Hisham.Reduction;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class LongestPath {

    private final boolean[] onPath;
    private final Stack<Integer> path;
    private Stack<Integer> result;
    private int max_len;
    private int len;

    public LongestPath(Graph g) {
        onPath = new boolean[g.V()];
        path = new Stack<>();
        result = new Stack<>();
        max_len = Integer.MIN_VALUE;
        len = 0;
        for (int v = 0; v < g.V(); v++) {
            if (!onPath[v]) {
                dfs(g, v);
            }
        }
    }

    private void dfs(Graph g, int v) {
        path.push(v);
        onPath[v] = true;

        for (int w : g.adj(v)) {
            if (!onPath[w]) {
                len++;
                dfs(g, w);
            }
        }

        if (len > max_len) {
            max_len = len;
            result = path;
            printCurrentPath();
        }
        path.pop();
        onPath[v] = false;
        len = 0;
    }

    private void printCurrentPath() {
        Stack<Integer> reverse = new Stack<>();
        for (int v : path) {
            reverse.push(v);
        }
        while (!reverse.isEmpty()) {
            System.out.print(" " + reverse.pop());
        }
        System.out.println();
    }

    public int max_len() {
        return max_len;
    }

    public static void main(String[] args) {
        Graph G = new Graph(7);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(2, 5);
        G.addEdge(1, 5);
        G.addEdge(5, 4);
        G.addEdge(3, 6);
        G.addEdge(4, 6);
        StdOut.println(G);

        LongestPath l = new LongestPath(G);
        System.out.println(l.max_len());
        l.printCurrentPath();

    }
}
