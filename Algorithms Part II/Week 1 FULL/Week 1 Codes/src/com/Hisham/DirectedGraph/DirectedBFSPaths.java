package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

public class DirectedBFSPaths {   //for both single and multiple sources

    private final boolean[] marked;
    private final int[] edgeTo;
    private final int[] distTo;   // number of edges shortest s-v path

    public DirectedBFSPaths(Digraph g, int s) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];
        bfs(g, s);
    }

    public DirectedBFSPaths(Digraph g, Iterable<Integer> sources) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];
        for (int v = 0; v < g.V(); v++) {
            distTo[v] = Integer.MAX_VALUE;
        }
        bfs(g, sources);
    }

    private void bfs(Digraph g, int s) {
        Queue<Integer> queue = new Queue<>();
        for (int v = 0; v < g.V(); v++) {
            distTo[v] = Integer.MAX_VALUE;
        }
        queue.enqueue(s);
        marked[s] = true;
        distTo[s] = 0;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }
        }
    }

    private void bfs(Digraph g, Iterable<Integer> sources) {
        Queue<Integer> queue = new Queue<>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            queue.enqueue(s);
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }
        }
    }

    public boolean hasPathTo(int v) {   // has shortest path??
        return marked[v];
    }

    public int distTo(int v) {   // shortest path length for vertex v
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> stack = new Stack<>();
        int x;
        for (x = v; distTo(x) != 0; x = edgeTo[x]) {
            stack.push(x);
        }
        stack.push(x);
        return stack;
    }

    public static void main(String[] args) {
        int s = Integer.parseInt(StdIn.readLine());
        In in = new In(StdIn.readLine());
        Digraph G = new Digraph(in);
        // StdOut.println(G);
        DirectedBFSPaths bfs = new DirectedBFSPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else StdOut.print("-" + x);
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }
        }
    }
}
