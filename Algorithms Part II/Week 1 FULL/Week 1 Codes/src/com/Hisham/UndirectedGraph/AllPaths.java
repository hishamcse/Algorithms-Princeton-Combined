package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class AllPaths {

    private final boolean[] onPath;
    private final Stack<Integer> path;
    private int numOfPaths;

    public AllPaths(Graph g,int s,int t){
        onPath=new boolean[g.V()];
        path=new Stack<>();
        numOfPaths=0;
        dfs(g,s,t);
    }

    private void dfs(Graph g,int v,int t){
        path.push(v);
        onPath[v]=true;

        if(v==t){
            printCurrentPath();
            numOfPaths++;
        }else{
            for (int w:g.adj(v)){
                if(!onPath[w]){
                    dfs(g,w,t);
                }
            }
        }

        path.pop();
        onPath[v]=false;
    }

    private void printCurrentPath(){
        Stack<Integer> reverse=new Stack<>();
        for(int v:path){
            reverse.push(v);
        }
        while (!reverse.isEmpty()){
            System.out.print(" "+reverse.pop());
        }
        System.out.println();
    }

    public int totalPaths(){
        return numOfPaths;
    }

    public static void main(String[] args) {
        Graph G = new Graph(7);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(2, 5);
        G.addEdge(1, 5);
        G.addEdge(5, 4);
        G.addEdge(3, 6);
        G.addEdge(4, 6);
        StdOut.println(G);

        StdOut.println();
        StdOut.println("all simple paths between 0 and 6:");
        AllPaths paths1 = new AllPaths(G, 0, 6);
        StdOut.println("# paths = " + paths1.totalPaths());

        StdOut.println();
        StdOut.println("all simple paths between 1 and 5:");
        AllPaths paths2 = new AllPaths(G, 1, 5);
        StdOut.println("# paths = " + paths2.totalPaths());
    }

}
