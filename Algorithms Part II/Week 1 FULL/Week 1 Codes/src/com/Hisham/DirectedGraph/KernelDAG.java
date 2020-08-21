package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.KosarajuSharirSCC;

public class KernelDAG {

    private final int[] id;
    private final boolean[] marked;
    private int count;
    private final Digraph n;

    public KernelDAG(Digraph g){

        id=new int[g.V()];
        count=0;
        marked=new boolean[g.V()];

        KosarajuSharirSCC scc=new KosarajuSharirSCC(g);
        int c=scc.count();

        n=new Digraph(c);
        edu.princeton.cs.algs4.DepthFirstOrder dfs=new DepthFirstOrder(g.reverse());
        for(int v:dfs.reversePost()){
            if(!marked[v]){
                dfs_kernel(g,v);
                count++;
            }
        }

        for(int v:dfs.reversePost()){
            for(int w:g.adj(v)){
                if(id[v]!=id[w]){
                    n.addEdge(id[v],id[w]);
                }
            }
        }

    }

    private void dfs_kernel(Digraph g, int v){
        id[v]=count;
        marked[v]=true;
        for(int w:g.adj(v)){
            if(!marked[w]){
                dfs_kernel(g,w);
            }
        }
    }

    public Digraph kernelDAG(){
        return n;
    }

    public static void main(String[] args) {
        In in=new In(StdIn.readLine());
        Digraph g=new Digraph(in);
        KernelDAG kd=new KernelDAG(g);
        System.out.println("Kernel DAG = "+kd.kernelDAG());
    }
}
