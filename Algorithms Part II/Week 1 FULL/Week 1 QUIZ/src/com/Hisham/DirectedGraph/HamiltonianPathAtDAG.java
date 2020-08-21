package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.*;

public class HamiltonianPathAtDAG {

    //not properly correct (perfect solution is next)
    public Iterable<Integer> hamiltonianPathAtDAG(Digraph g){
        Digraph rev=g.reverse();
        int v=findEnd(rev, 0);
        if(v>=0){
            int count=1;
            Queue<Integer> path=new Queue<>();
            while (g.outdegree(v)==1){
                path.enqueue(v);
                for(int w:g.adj(v)){
                    v=w;
                }
                count++;
            }
            path.enqueue(v);
            if(g.V()==count){
                return path;
            }
        }
        return null;
    }

    //perfect solution
    public Iterable<Integer> hamiltonSecondWay(Digraph g){
        Topological t=new Topological(g);
        Iterable<Integer> i=t.order();
        TransitiveClosure r=new TransitiveClosure(g);
        for(int v:i){
            if(v+1<g.V() && (r.reachable(v,v+1) || r.reachable(v+1,v))){
                continue;
            }else if(v+1<g.V()){
                return null;
            }
        }
        return i;
    }

    private int findEnd(Digraph g,int v){
        if(g.outdegree(v)==0){
            return v;
        }
        if(g.outdegree(v)==1){
            for(int i:g.adj(v)){
                return findEnd(g,i);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        In in=new In(StdIn.readLine());
        Digraph g=new Digraph(in);
        HamiltonianPathAtDAG h=new HamiltonianPathAtDAG();
        System.out.println(h.hamiltonianPathAtDAG(g));
        System.out.println(h.hamiltonSecondWay(g));
    }
}
