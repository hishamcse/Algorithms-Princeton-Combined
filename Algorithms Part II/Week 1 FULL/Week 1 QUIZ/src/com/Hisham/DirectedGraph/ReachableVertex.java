package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

public class ReachableVertex {

    private int[] id;
    private boolean[] marked;
    private int count;
    private boolean reachableDAG;
    private boolean reachableDigraph;

    public int reachableVertexInDAG(Digraph g){
        DirectedCycle d=new DirectedCycle(g);
        if(!d.hasCycle()){        // A DAG cant have a cycle
            int count=0;
            int v;
            for(v=0;v<g.V();v++){
                if(g.outdegree(v)==0){
                    count++;
                }
            }
            if(count==1){
                reachableDAG=true;
                return v;
            }else{
                return -1;
            }
        }
        return -1;
    }

    private void dfs_kernel(Digraph g,int v){
        id[v]=count;
        marked[v]=true;
        for(int w:g.adj(v)){
            if(!marked[w]){
                dfs_kernel(g,w);
            }
        }
    }

    // kernel process (see slide-pg 58 & KernelDAG.java)
    public int reachableVertexInDigraph(Digraph g){
        DirectedCycle d=new DirectedCycle(g);
        if(!d.hasCycle()){
            int l=reachableVertexInDAG(g);
            if(l!=-1){
                reachableDigraph=true;
                return l;
            }
            return -1;
        }

        id=new int[g.V()];
        count=0;
        marked=new boolean[g.V()];

        KosarajuSharirSCC scc=new KosarajuSharirSCC(g);
        int c=scc.count();

        Digraph n=new Digraph(c);
        DepthFirstOrder dfs=new DepthFirstOrder(g.reverse());
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

        // n is now a kernel DAG
        int l=reachableVertexInDAG(n);
        if(l!=-1){
            reachableDigraph=true;
            return l;
        }
        return -1;
    }

    public boolean isReachableDAG(){
        return reachableDAG;
    }

    public boolean isReachableDigraph(){
        return reachableDigraph;
    }

    public static void main(String[] args) {
        In in=new In(StdIn.readLine());
        Digraph g=new Digraph(in);
        ReachableVertex r=new ReachableVertex();
        int v=r.reachableVertexInDAG(g);
        System.out.println("DAG reachable = "+r.isReachableDAG()+" vertex = "+v);
        int k=r.reachableVertexInDigraph(g);
        System.out.println("Digraph reachable = " + r.isReachableDigraph() + " vertex = " + k);
    }

}
