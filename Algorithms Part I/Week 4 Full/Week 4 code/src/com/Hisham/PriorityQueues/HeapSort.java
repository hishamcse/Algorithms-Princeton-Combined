package com.Hisham.PriorityQueues;

import edu.princeton.cs.algs4.StdIn;

public class HeapSort {

    public static void sort(Comparable[] a){
        int n=a.length;
        for(int k=n/2;k>=1;k--){
            sink(a,k,n);
        }
        int k=n;
        while(k>1){
            swap(a,1,k--);
            sink(a,1,k);
        }
    }

    public static void sink(Comparable[] a,int k,int n){
        while (2*k<=n){
            int j=2*k;
            if(j<n && less(a,j,j+1)){
                j++;
            }
            if(!less(a,k,j)){
                break;
            }
            swap(a,k,j);
            k=j;
        }
    }

    public static boolean less(Comparable[] a,int i,int j){
        return a[i-1].compareTo(a[j-1])<0;
    }

    public static void swap(Comparable []a,int i,int j){
        Comparable swapval=a[i-1];
        a[i-1]=a[j-1];
        a[j-1]=swapval;
    }

    public static void main(String[] args) {
        int n= StdIn.readInt();
        Integer[] a=new Integer[n];
        for(int i=0;i<n;i++){
            a[i]=StdIn.readInt();
        }
        sort(a);
        for(int i=0;i<n;i++){
            System.out.print(a[i]+" ");
        }
    }
}
