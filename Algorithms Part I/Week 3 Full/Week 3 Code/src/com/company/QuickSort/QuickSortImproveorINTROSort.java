package com.company.QuickSort;

import edu.princeton.cs.algs4.StdRandom;

import java.security.SecureRandom;

public class QuickSortImproveorINTROSort {

    private static final int CUTOFF=10;

    public static int partition(Comparable[] a,int lo,int hi){
        int i=lo;
        int j=hi+1;

        while(true){
            while(less(a[++i],a[lo])){
                if(i==hi){
                    break;
                }
            }
            while (less(a[lo],a[--j])){
                if(j==lo){
                    break;
                }
            }
            if(i>=j){
                break;
            }
            swap(a,i,j);
        }
        swap(a,lo,j);
        return j;
    }

    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--)
                swap(a, j, j - 1);
    }

    private static int medianof3(Comparable[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
                (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
                (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }


    public static void sort(Comparable[] a, int lo, int hi){
        if(hi<=lo+CUTOFF-1){  //improvement 1
            insertionSort(a,lo,hi);
            return;
        }

        //improvement 2
        int m= medianof3(a,lo,lo+(hi-lo+1)/2,hi);    //or, lo+(a.length/2)
        swap(a,lo,m);

        int j=partition(a,lo,hi);
        sort(a,lo,j-1);
        sort(a,j+1,hi);
    }

    public static void sort(Comparable[] a){
        StdRandom.shuffle(a);
        int lo=0;
        int hi=a.length-1;
        sort(a,lo,hi);
    }

    private static boolean less(Comparable a,Comparable b){
        return a.compareTo(b)<0;
    }

    private static void swap(Comparable[] a,int i,int j){
        Comparable temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }

    public static void main(String[] args) {
        SecureRandom random=new SecureRandom();
        int n= random.nextInt(1000);
        System.out.println(n);
        Integer []a=new Integer[n];
        for(int i=0;i<n;i++){
            a[i]= random.nextInt();
        }
        sort(a);
        for(int i=0;i<n;i++){
            System.out.println(a[i]);
        }
    }
}
