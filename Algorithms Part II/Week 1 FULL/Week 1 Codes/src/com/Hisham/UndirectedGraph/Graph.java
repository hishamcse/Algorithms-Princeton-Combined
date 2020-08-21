package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;

public class Graph {  //implements Adjacency List Representation for undirected graph(this is the most efficient one)

    private final int V;
    private int E;
    private final Bag<Integer>[] adj;

    public Graph(int V){
        this.V=V;
        this.E=0;
        adj=(Bag<Integer> []) new Bag[V];
        for(int i=0;i<V;i++){
            adj[i]=new Bag<>();
        }
    }

    public Graph(In in){
        try{
           this.V=in.readInt();
           this.adj=(Bag<Integer>[]) new Bag[this.V];
           for(int i=0;i<this.V;i++){
               this.adj[i]=new Bag<>();
           }
           int E=in.readInt();
           for(int i=0;i<E;i++){
               int v=in.readInt();
               int w=in.readInt();
               addEdge(v,w);
           }
        }catch (NoSuchElementException e){
            throw new IllegalArgumentException();
        }
    }

    public Graph(Graph g){
        this.V=g.V();
        this.E=g.E();
        this.adj=(Bag<Integer>[]) new Bag[this.V];

        for(int i=0;i<this.V();i++){
            adj[i]=new Bag<>();
        }

        for(int v=0;v<g.V();v++){
            //as we want the exact order to be followed as the parameter graph.
            // so,reverse.because,Bag adds item at the first

            Stack<Integer> stack=new Stack<>();
            for(Integer w:g.adj(v)){
                stack.push(w);
            }
            for(Integer w:stack){
                adj[v].add(w);
            }
        }
    }

    public void addEdge(int v,int e){
        E++;
        adj[v].add(e);
        adj[e].add(v);
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public int degree(int v){
        return adj[v].size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" vertices, ").append(E).append(" edges ").append("\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (int w : adj[v]) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readString());
        Graph G = new Graph(in);
        StdOut.println(G);
    }
}
