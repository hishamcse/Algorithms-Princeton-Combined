package com.company.MergeSort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class MergeSort {

    public static void merge(Comparable[] a,Comparable[] aux,int lo,int hi,int mid){

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
            }
        }

        assert isSorted(a,lo,hi);
    }

    public static void sort(Comparable[] a,Comparable[] aux,int lo,int hi){

        //improvement 1(for small subarrays.CUTOFF maximum is 7)
//        if(hi<=lo+CUTOFF-1){
//            Insertion.sort(a,lo,hi);
//            return;
//        }

        if(hi<=lo){
            return;
        }
        int mid=lo+(hi-lo)/2;
        sort(a,aux,lo,mid);
        sort(a,aux,mid+1,hi);

        //improvement 2(if already sorted,then return)
        if(less(a[mid],a[mid+1])){
            return;
        }

        merge(a,aux,lo,hi,mid);


        //improvement 3(saving time,by switching the role of a and aux during each recursion)
//        sort(aux,a,lo,mid);
//        sort(aux,a,mid+1,hi);
//        merge(a,aux,lo,hi,mid);

    }


    public static void sort(Comparable[] a){
        Comparable[] aux=new Comparable[a.length];
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
