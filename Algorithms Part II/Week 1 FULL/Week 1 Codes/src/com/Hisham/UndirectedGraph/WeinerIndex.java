package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class WeinerIndex {   // see booksite for problem details

    public int weinerIndexForVertex(Graph g, int v){
        int sum=0;
        BreadthFirstPaths bfs=new BreadthFirstPaths(g,v);
        for(int w=0;w<g.V() && w!=v ;w++){
            sum+=bfs.distTo(w);
        }
        return sum;
    }

    public int weinerIndexForGraph(Graph g){
        int sum=0;
        for(int v=0;v<g.V();v++){
            sum+=weinerIndexForVertex(g,v);
        }
        return sum;
    }

    public static void main(String[] args) {

        //ans is 250 for weiner.txt and 138 for weiner2.txt

        In in=new In(StdIn.readLine());
        Graph g=new Graph(in);
        WeinerIndex weinerIndex=new WeinerIndex();
        System.out.println("weiner index for the whole graph = "+weinerIndex.weinerIndexForGraph(g));
        System.out.println("weiner index for all vertices :");
        for(int v=0;v<g.V();v++){
            System.out.println(v+" : "+ weinerIndex.weinerIndexForVertex(g,v));
        }
    }
}
