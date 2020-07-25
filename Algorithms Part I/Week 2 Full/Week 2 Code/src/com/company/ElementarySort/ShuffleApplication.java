package com.company.ElementarySort;

import edu.princeton.cs.algs4.StdRandom;

public class ShuffleApplication {

    public static void shuffle(Object []a){
        int n=a.length;

        for(int i=0;i<n;i++){
           int r= StdRandom.uniform(i+1);
           swap(a,i,r);
        }
    }

    public static void swap(Object []a,int i,int j){
        Object swapval=a[i];
        a[i]=a[j];
        a[j]=swapval;
    }
}
