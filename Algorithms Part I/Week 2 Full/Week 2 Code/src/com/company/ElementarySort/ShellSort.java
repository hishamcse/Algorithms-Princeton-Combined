package com.company.ElementarySort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.time.Duration;
import java.time.Instant;

public class ShellSort {

    public static void Sort(Comparable[] a){
       int n=a.length;
       int h=1;
       while(h<n/3){
           h=3*h+1;
       }
       while(h>=1){
           for(int i=h;i<n;i++){
               for(int j=i;j>=h && less(a[j],a[j-h]);j-=h){
                   swap(a,j,j-h);
               }
           }
           h/=3;
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
