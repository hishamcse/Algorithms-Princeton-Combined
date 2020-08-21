package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

public class GabowSCC {

    // quite similar to Tarjan algorithm.just using 2 stacks to make some simplification

    private final boolean[] marked;       // marked[v] = has v been visited?
    private final int[] id;               // id[v] = id of strong component containing v
    private final int[] preorder;         // preorder[v] = preorder of v
    private int pre;                      // preorder number counter
    private int count;                    // number of strongly-connected components
    private final Stack<Integer> stack1;
    private final Stack<Integer> stack2;

    public GabowSCC(Digraph g) {
        marked = new boolean[g.V()];
        id = new int[g.V()];
        preorder = new int[g.V()];
        pre = 0;
        count = 0;
        stack1 = new Stack<>();
        stack2 = new Stack<>();

        for (int v = 0; v < g.V(); v++) {
            id[v] = -1;
        }

        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) {
                dfs(g, v);
            }
        }
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        preorder[v] = pre++;
        stack1.push(v);
        stack2.push(v);
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            } else if (id[w] == -1) {
                while (preorder[stack2.peek()] > preorder[w]) {
                    stack2.pop();
                }
            }
        }
        if (stack2.peek() == v) {
            stack2.pop();
            int w;
            do {
                w = stack1.pop();
                id[w] = count;
            } while (w != v);
            count++;
        }
    }

    public int count() {
        return count;
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        Digraph G = new Digraph(in);
        GabowSCC scc = new GabowSCC(G);

        // number of connected components
        int m = scc.count();
        StdOut.println(m + " components");

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

    }
}
