package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.SymbolGraph;

public class DegreesOfSeparation {

    public static void main(String[] args) {
        String filename = StdIn.readLine();
        String delimiter = StdIn.readLine();
        String source = StdIn.readLine();

        SymbolGraph symbolGraph=new SymbolGraph(filename,delimiter);
        Graph g=symbolGraph.graph();
        if(!symbolGraph.contains(source)){
            System.out.println("does not contain");
            return;
        }

        int s=symbolGraph.indexOf(source);
        BreadthFirstPaths b=new BreadthFirstPaths(g,s);
//        DepthFirstPaths b=new DepthFirstPaths(g,s);

        while (!StdIn.isEmpty()){
            String sink=StdIn.readLine();
            if(symbolGraph.contains(sink)){
                int t=symbolGraph.indexOf(sink);
                if(b.hasPathTo(t)){
                    for(int w:b.pathTo(t)){
                        System.out.println(" "+symbolGraph.nameOf(w));
                    }
                }else{
                    System.out.println("not connected");
                }
            }else{
                System.out.println("not in database");
            }
        }
    }
}
