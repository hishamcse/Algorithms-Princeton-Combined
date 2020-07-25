package com.company;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class UnionFindCanonical {
    private WeightedQuickUnionUF uf;
    private int[] max;
    private int[] parent;
    private int[] size;
    private int total;

    public UnionFindCanonical(int n){
        uf=new WeightedQuickUnionUF(n);
        max=new int[n];
        parent=new int[n];
        size=new int[n];
        total=n;
        for(int i=0;i<n;i++){
           max[i]=i;
           parent[i]=i;
           size[i]=1;
        }
    }

    public int find(int i){
        while (i != parent[i]){
            i = parent[i];
        }
        return i;
    }

    public boolean connected(int p,int q){
        return uf.find(p)==uf.find(q);
    }

    public void union(int p,int q){
        uf.union(p,q);
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
            max[rootQ]=Math.max(max[rootP],max[rootQ]);
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
            max[rootP]=Math.max(max[rootP],max[rootQ]);
        }
    }

    public int count(){
        return uf.count();
    }
    public int findMax(int p){

        //process 1(but complexity O(n))
//        int q=uf.find(p);
//        System.out.println(q);
//        for(int i=total-1;i>=0;i--){
//            if(uf.find(i)==q){
//                return i;
//            }
//        }
//        return -1;

        //process 2()
        int q=uf.find(p);
        return max[q];

    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        UnionFindCanonical uf = new UnionFindCanonical(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if(p==-1){
                break;
            }
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
        int i=StdIn.readInt();
        StdOut.println("Max: " + i + " = " + uf.findMax(i));
    }
}
