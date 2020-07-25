package com.company.MergeSort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Quiz1 {

    public static void merge(int[] a,int lo,int hi){

        assert hi>lo;
        int mid=lo+(hi-lo)/2;
        assert isSorted(a,lo,mid);
        assert isSorted(a,mid+1,hi);

        int i,j,k;
        int[] aux=new int[mid-lo+1]; //as we are allowed to use an array of size at most n

        for(i=lo;i<=mid;i++){
            aux[i-lo]=a[i];
        }

        i=lo;j=mid+1;

        for(k=lo;k<=hi;k++){
            if(j>hi){
                a[k]=aux[i++];
            }else if(i==aux.length){
                a[k]=a[j++];
            }else if(aux[i]<=a[j]){
                a[k]=aux[i++];
            }else{
                a[k]=a[j++];
            }
        }
    }

    public static void sort(int[] a){
        merge(a,0,a.length-1);
    }

    public static boolean less(int a,int b){
        return a < b;
    }

    public static boolean isSorted(int []a,int start,int end){
        for(int i=start+1;i<=end;i++){
            if(less(a[i],a[i-1])){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n= StdIn.readInt();
        int []a=new int[n];
        for(int i=0;i<n;i++){
            a[i]= StdRandom.uniform(n);
        }
        sort(a);
        for(int i=0;i<n;i++){
            System.out.println(a[i]);
        }
    }
}
