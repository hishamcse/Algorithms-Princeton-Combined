package com.company.MergeSort;

import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class MergeSortImprovement3 {
    //seebooksite MergeX.java for full implementation

    public static final int CUTOFF=7;

    public static void merge(Comparable[] a,Comparable[] aux,int lo,int hi,int mid){
        int i,j,k;
        i=lo;j=mid+1;

        for(k=lo;k<=hi;k++){
            if(i>mid){
                a[k]=aux[j];
                j++;
            }else if(j>hi){
                a[k]=aux[i];
                i++;
            } else if(less(aux[i],aux[j])){
                a[k]=aux[i];
                i++;
            } else{
                a[k]=aux[j];
                j++;
            }
        }
    }

    public static void sort(Comparable[] a,Comparable[] aux,int lo,int hi){

//        if(hi<=lo+CUTOFF){
//            Insertion.sort(a,lo,hi);
//            return;
//        }

        if(hi<=lo){
            return;
        }

        int mid=lo+(hi-lo)/2;
        sort(aux,a,lo,mid);
        sort(aux,a,mid+1,hi);
//        if (!less(a[mid+1], a[mid])) {
//            System.arraycopy(a, lo, aux, lo, hi - lo + 1);
//            return;
//        }
        merge(a,aux,lo,hi,mid);

    }

    public static void sort(Comparable[] a){
        Comparable[] aux=new Comparable[a.length];
        System.arraycopy(a, 0, aux, 0, a.length);
        sort(a,aux,0,a.length-1);
    }

    public static boolean less(Comparable a,Comparable b){
        return a.compareTo(b)<0;
    }

    public static boolean isSorted(Comparable []a,int start,int end){
        for(int i=start+1;i<=end;i++){
            if(less(a[i],a[i-1])){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n= StdIn.readInt();
        Integer []a=new Integer[n];
        for(int i=0;i<n;i++){
            a[i]= StdRandom.uniform(n);
        }
        sort(a);
        for(int i=0;i<n;i++){
            System.out.println(a[i]);
        }
    }
}
