package com.company.MergeSort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class QUIZ2 {
    public static long merge(Comparable[] a,Comparable[] aux,int lo,int hi,int mid){

        long inversions=0;
        assert isSorted(a,lo,mid);
        assert isSorted(a,mid+1,hi);

        for(int i=lo;i<=hi;i++){
            aux[i]=a[i];
        }

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
                inversions+=(mid-i+1);
            }
        }

        assert isSorted(a,lo,hi);
        return inversions;
    }

    public static long sort(Comparable[] a,Comparable[] aux,int lo,int hi){

        long inversions=0;

        if(hi<=lo){
            return 0;
        }
        int mid=lo+(hi-lo)/2;
        inversions+=sort(a,aux,lo,mid);
        inversions+=sort(a,aux,mid+1,hi);

        inversions+=merge(a,aux,lo,hi,mid);

        return inversions;

    }


    public static long sort(Comparable[] a){
        Comparable[] aux=new Comparable[a.length];
        return sort(a,aux,0,a.length-1);
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
