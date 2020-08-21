package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class GraphClient {

    public static int degree(Graph g,int v){
        int deg=0;
        for(Integer ignored :g.adj(v)){
            deg++;
        }
        return deg;
    }

    public static int maxDegree(Graph g){
        int max=0;
        for(int v=0;v<g.V();v++){
            if(degree(g,v)>max){
                max=degree(g,v);
            }
        }
        return max;
    }

    public static double avgDegree(Graph g){
        return 2.0*g.E()/g.V();
    }

    public static int numberOfSelfLoops(Graph g){
        int s=0;
        for(int v=0;v<g.V();v++){
            for(Integer w:g.adj(v)){
                if(v==w){
                    s++;
                }
            }
        }
        return s/2;
    }

    public static void main(String[] args) {
        In in=new In(StdIn.readString());
        Graph graph=new Graph(in);

        for(int v=0;v< graph.V();v++){
            for(Integer w:graph.adj(v)){
                System.out.println(v+" - "+w);
            }
        }
        System.out.println(degree(graph,1));
        System.out.println(maxDegree(graph));
        System.out.println(avgDegree(graph));
        System.out.println(numberOfSelfLoops(graph));
    }
}
