package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

public class TransitiveClosure {

    private final DirectedDFS[] tc;

    public TransitiveClosure(Digraph g){
        tc=new DirectedDFS[g.V()];
        for(int v=0;v<g.V();v++){
            tc[v]=new DirectedDFS(g,v);
        }
    }

    public boolean reachable(int v,int w){
        return tc[v].marked(w);
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        Digraph G = new Digraph(in);

        TransitiveClosure tc = new TransitiveClosure(G);

        // print header
        StdOut.print("     ");
        for (int v = 0; v < G.V(); v++)
            StdOut.printf("%3d", v);
        StdOut.println();
        StdOut.println("--------------------------------------------");

        // print transitive closure
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (tc.reachable(v, w)) StdOut.printf("  T");
                else                    StdOut.printf("   ");
            }
            StdOut.println();
        }
    }

}
