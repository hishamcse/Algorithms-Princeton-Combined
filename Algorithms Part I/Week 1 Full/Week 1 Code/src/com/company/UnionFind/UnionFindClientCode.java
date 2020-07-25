package com.company.UnionFind;

import edu.princeton.cs.algs4.StdIn;

public class UnionFindClientCode {
    public static void main(String[] args) {
        int n= StdIn.readInt();
//        UF uf=new UF(n);
//        QuickFindUF uf=new QuickFindUF(n);
//        QuickUnionUF uf=new QuickUnionUF(n);
//        WeightedQuickUnionUF uf=new WeightedQuickUnionUF(n);
        WeightedQuickUnionUFPathCompression uf=new WeightedQuickUnionUFPathCompression(n);
        while(!StdIn.isEmpty()){
            int p=StdIn.readInt();
            int q=StdIn.readInt();

            if(!uf.connected(p,q)){
                uf.union(p,q);
                System.out.println(p+" "+q);
            }
        }
    }
}
