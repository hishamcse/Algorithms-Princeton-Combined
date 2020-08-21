package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DirectedDFSSearch {   //for both single and multiple sources

    private final boolean[] marked;
    private int count;

    public DirectedDFSSearch(Digraph g, int s) {
        count = 0;
        marked = new boolean[g.V()];
        dfs(g, s);
    }

    public DirectedDFSSearch(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int v : sources) {
            if (!marked[v]) dfs(G, v);
        }
    }

    private void dfs(Digraph g, int v) {
        count++;
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public int count() {
        return count;
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        int s = Integer.parseInt(StdIn.readLine());
        In in = new In(StdIn.readString());
        Digraph G = new Digraph(in);
        DirectedDFSSearch search = new DirectedDFSSearch(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }
        StdOut.println();
        if (search.count() != G.V()) StdOut.println("NOT connected");
        else StdOut.println("connected");
    }
}
