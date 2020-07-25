package com.company.MergeSort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;

public class InsertionSortUsingComparator {

    public static void Sort(Object[] a, Comparator c){
        for(int i=0;i<a.length;i++){
            for(int j=i;j>0;j--){
                if(less(c,a[j],a[j-1])){
                   swap(a,j,j-1);
                }else{
                    break;
                }
            }
        }
    }

    public static boolean less(Comparator c,Object a,Object b){
        return c.compare(a,b)<0;
    }

    public static void swap(Object []a,int i,int j){
        Object swapval=a[i];
        a[i]=a[j];
        a[j]=swapval;
    }

    public static void main(String[] args) {
        int n= StdIn.readInt();
        Integer[] a=new Integer[n];
        for(int i=0;i<n;i++){
            a[i]= StdRandom.uniform(n);
        }
        Instant instant= Instant.now();
//        Sort(a,PLACE_ORDER);
        Instant instant1=Instant.now();
        System.out.println("time taken= "+ Duration.between(instant,instant1).toMillis());
        for(int i=0;i<n;i++){
            System.out.println(a[i]);
        }
    }
}
