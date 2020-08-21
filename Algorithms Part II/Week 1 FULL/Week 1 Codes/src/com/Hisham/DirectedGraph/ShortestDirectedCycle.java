package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

public class ShortestDirectedCycle {

    private Stack<Integer> cycle;
    private int length;

    public ShortestDirectedCycle(Digraph g) {
        Digraph r=g.reverse();
        length=g.V()+1;
        for(int v=0;v<g.V();v++){
            BreadthFirstDirectedPaths bfs=new BreadthFirstDirectedPaths(r,v);
            // this should be reverse & iterate should be original.So,it will be guaranted that
            // there exists a cycle. v -> w && w -> v

            for(int w:g.adj(v)){
                if(bfs.hasPathTo(w) && bfs.distTo(w)+1<length){
                    length=bfs.distTo(w)+1;
                    cycle=new Stack<>();
                    for(int t:bfs.pathTo(w)){
                        cycle.push(t);
                    }
                    cycle.push(v);
                }
            }
        }
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public int cycleLength(){
        return length;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        Digraph G = new Digraph(in);

        ShortestDirectedCycle finder = new ShortestDirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.println("Shortest Directed cycle of length: "+finder.cycleLength());
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("No directed cycle");
        }
        StdOut.println();
    }
}
