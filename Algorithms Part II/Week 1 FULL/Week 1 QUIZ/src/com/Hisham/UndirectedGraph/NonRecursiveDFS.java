package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class NonRecursiveDFS {

    private final boolean[] marked;
    private int count;

    public NonRecursiveDFS(Graph g, int s) {
        count = 0;
        marked = new boolean[g.V()];
        dfsNonRecursive(g, s);
    }

    private void dfs(Graph g, int s) {
        Stack<Integer> stack=new Stack<>();
        count++;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v= stack.pop();
            if(!marked[v]) {
                marked[v] = true;
                for (int w : g.adj(v)) {
                    if (!marked[w]) {
                        stack.push(w);
                        count++;
                    }
                }
            }
        }
    }

    //another implementation
    private void dfsNonRecursive(Graph g, int s){
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[g.V()];
        for (int v = 0; v < g.V(); v++)
            adj[v] = g.adj(v).iterator();

        // depth-first search using an explicit stack
        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }
            }
            else {
                stack.pop();
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
        Graph G = new Graph(in);
        DepthFirstSearch search = new DepthFirstSearch(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }
        StdOut.println();
        if (search.count() != G.V()) StdOut.println("NOT connected");
        else StdOut.println("connected");
    }
}
