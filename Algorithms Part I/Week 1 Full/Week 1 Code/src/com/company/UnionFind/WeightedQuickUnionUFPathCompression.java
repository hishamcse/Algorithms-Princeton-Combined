package com.company.UnionFind;

public class WeightedQuickUnionUFPathCompression {
    private int[] id;
    private int[] sz={0};

    public WeightedQuickUnionUFPathCompression(int n){
        id=new int[n];
        for(int i=0;i<n;i++){
            id[i]=i;
        }
    }

    private int root(int i){
        while(i!=id[i]){
            id[i]=id[id[i]];
            i=id[i];}
        return i;
    }

    public boolean connected(int p,int q){
        return root(p)==root(q);
    }

    public void union(int p,int q){
        int i=root(p);
        int j=root(q);
        if(i==j){return;}
        if(sz[i]>sz[j]){id[j]=i; sz[i]+=sz[j];}
        else{id[i]=j; sz[j]+=sz[i];}
    }
}
