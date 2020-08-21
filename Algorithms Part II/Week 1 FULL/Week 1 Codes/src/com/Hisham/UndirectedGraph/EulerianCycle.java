package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Graph;

public class EulerianCycle {        // search booksite as EulerianCycle.java

    private Stack<Integer> cycle;

    private static class Edge{
        private final int v;
        private final int w;
        private boolean isUsed;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
            isUsed=false;
        }

        public int other(int s){
            if(s==v){
                return w;
            }else if(s==w){
                return v;
            }else{
                throw new IllegalArgumentException("argument illegal");
            }
        }
    }

    public EulerianCycle(edu.princeton.cs.algs4.Graph g){
        if(g.E()==0){
            return;
        }
        for(int v=0;v<g.V();v++){
            if(g.degree(v)%2!=0){
                return;
            }
        }

        Queue<Edge>[] adj=(Queue<Edge>[]) new Queue[g.V()];
        createLocalAdjList(g,adj);

        createCycle(g,adj);
    }

    // create local view of adjacency lists, to iterate one vertex at a time
    // the helper Edge data type is used to avoid exploring both copies of an edge v-w
    private void createLocalAdjList(edu.princeton.cs.algs4.Graph g, Queue<Edge>[] adj){
        for(int v=0;v<g.V();v++){
            adj[v]=new Queue<>();
        }
        for(int v=0;v<g.V();v++){
            int selfLoops=0;
            for(int w:g.adj(v)){
                if(v==w){
                    if(selfLoops%2==0){
                        Edge e=new Edge(v,w);
                        adj[v].enqueue(e);
                        adj[w].enqueue(e);
                    }
                    selfLoops++;
                } else if(v<w){
                    Edge e=new Edge(v,w);
                    adj[v].enqueue(e);
                    adj[w].enqueue(e);
                }
            }
        }
    }

    private void createCycle(edu.princeton.cs.algs4.Graph g, Queue<Edge>[] adj){
        int s=nonIsolatedVertex(g);
        Stack<Integer> stack=new Stack<>();
        stack.push(s);

        cycle=new Stack<>();
        while (!stack.isEmpty()){
            int v=stack.pop();
            while (!adj[v].isEmpty()){
                Edge edge=adj[v].dequeue();
                if(edge.isUsed){continue;}
                edge.isUsed=true;
                stack.push(v);
                v=edge.other(v);
            }
            cycle.push(v);
        }

        if(cycle.size()!=g.E()+1){
            cycle=null;
        }
    }

    private int nonIsolatedVertex(edu.princeton.cs.algs4.Graph g){
        for(int v=0;v<g.V();v++){
            if(g.degree(v)>0){
                return v;
            }
        }
        return -1;
    }

    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    private static void unitTest(edu.princeton.cs.algs4.Graph G, String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);

        EulerianCycle euler = new EulerianCycle(G);

        StdOut.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("none");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());

        // Eulerian cycle
        edu.princeton.cs.algs4.Graph G1 = GraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");

        // Eulerian path
        edu.princeton.cs.algs4.Graph G2 = GraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");

        // empty graph
        edu.princeton.cs.algs4.Graph G3 = new edu.princeton.cs.algs4.Graph(V);
        unitTest(G3, "empty graph");

        // self loop
        edu.princeton.cs.algs4.Graph G4 = new edu.princeton.cs.algs4.Graph(V);
        int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");

        // union of two disjoint cycles
        edu.princeton.cs.algs4.Graph H1 = GraphGenerator.eulerianCycle(V/2, E/2);
        edu.princeton.cs.algs4.Graph H2 = GraphGenerator.eulerianCycle(V - V/2, E - E/2);
        int[] perm = new int[V];
        for (int i = 0; i < V; i++)
            perm[i] = i;
        StdRandom.shuffle(perm);
        edu.princeton.cs.algs4.Graph G5 = new edu.princeton.cs.algs4.Graph(V);
        for (int v = 0; v < H1.V(); v++)
            for (int w : H1.adj(v))
                G5.addEdge(perm[v], perm[w]);
        for (int v = 0; v < H2.V(); v++)
            for (int w : H2.adj(v))
                G5.addEdge(perm[V/2 + v], perm[V/2 + w]);
        unitTest(G5, "Union of two disjoint cycles");

        // random digraph
        edu.princeton.cs.algs4.Graph G6 = GraphGenerator.simple(V, E);
        unitTest(G6, "simple graph");

        //from input file
        In in=new In(StdIn.readLine());
        edu.princeton.cs.algs4.Graph G7=new Graph(in);
        unitTest(G7,"Input File");
    }

}
