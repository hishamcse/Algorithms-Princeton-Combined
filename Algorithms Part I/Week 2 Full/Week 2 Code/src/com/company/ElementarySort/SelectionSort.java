package com.company.ElementarySort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.time.Duration;
import java.time.Instant;

public class SelectionSort {

    public static void Sort(Comparable[] a){
        int min;
        for(int i=0;i<a.length-1;i++){
            min=i;
            for(int j=i+1;j<a.length;j++){
                if(less(a[j],a[min])){
                    min=j;
                }
            }
            swap(a,i,min);
        }
    }

    public static boolean less(Comparable a,Comparable b){
        return a.compareTo(b)<0;
    }

    public static void swap(Comparable []a,int i,int j){
        Comparable swapval=a[i];
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
        Sort(a);
        Instant instant1=Instant.now();
        System.out.println("time taken= "+ Duration.between(instant,instant1).toMillis());
        for(int i=0;i<n;i++){
            System.out.println(a[i]);
        }
    }
}
