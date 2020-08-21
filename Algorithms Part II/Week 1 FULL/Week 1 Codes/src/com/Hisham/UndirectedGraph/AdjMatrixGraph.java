package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdjMatrixGraph {

    private final int V;
    private int E;
    private final boolean[][] adj;

    public AdjMatrixGraph(int V){
        this.V=V;
        this.E=0;
        adj=new boolean[this.V][this.V];
    }

    public AdjMatrixGraph(int V,int E){
        this(V);
        while (this.E!=E){
            int v= StdRandom.uniform(V);
            int w=StdRandom.uniform(V);
            addEdge(v,w);
        }
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(int v,int w){
        if(!adj[v][w]) E++;
        adj[v][w]=true;
        adj[w][v]=true;
    }

    public boolean contains(int V,int E){
        return adj[V][E];
    }

    public Iterable<Integer> adj(int v){
        return new AdjIterator(v);
    }

    private class AdjIterator implements Iterator<Integer>,Iterable<Integer>{
        private final int v;
        private int w=0;

        public AdjIterator(int v){
            this.v=v;
        }

        @Override
        public Iterator<Integer> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            while (w<V){
                if(adj[v][w]){
                    return true;
                }
                w++;
            }
            return false;
        }

        @Override
        public Integer next() {
            if(hasNext()){
                return w++;
            }
            throw new NoSuchElementException();
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" ").append(E);
        s.append("\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (int w : adj(v)) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int V = Integer.parseInt(StdIn.readLine());
        int E = Integer.parseInt(StdIn.readLine());
        AdjMatrixGraph G = new AdjMatrixGraph(V, E);
        StdOut.println(G);
        System.out.println(G.V());
        System.out.println(G.E());
        System.out.println(G.contains(1,7));
    }

}
