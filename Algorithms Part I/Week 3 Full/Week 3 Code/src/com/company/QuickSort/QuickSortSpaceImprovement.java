package com.company.QuickSort;

import edu.princeton.cs.algs4.StdRandom;

import java.security.SecureRandom;

public class QuickSortSpaceImprovement {  //known as Tail Recursion Elemination
//worst case space O(logn)

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

    public static void sort(Comparable[] a, int lo, int hi){
        if(hi<=lo){
            return;
        }

        //tail recursion elemination
//        while (hi>lo) {
//            int j = partition(a, lo, hi);
//            sort(a, lo, j - 1);
//            lo=j+1;
//        }

        //more effective tail recursion elemination(eleminating the largest portion)
        while (hi>lo) {
            int j = partition(a, lo, hi);
            if((j-lo)<(hi-j)) {
                sort(a, lo, j - 1);
                lo = j + 1;
            }else{
                sort(a,j+1,hi);
                hi=j-1;
            }
        }
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
