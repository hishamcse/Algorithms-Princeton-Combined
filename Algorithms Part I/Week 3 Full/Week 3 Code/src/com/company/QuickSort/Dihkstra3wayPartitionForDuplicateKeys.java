package com.company.QuickSort;

import edu.princeton.cs.algs4.StdRandom;

import java.security.SecureRandom;

public class Dihkstra3wayPartitionForDuplicateKeys {

    public static void sort(Comparable[] a,int lo,int hi){

        if(hi<=lo){
            return;
        }

        int lt=lo;
        int gt=hi;
        int i=lo;
        Comparable v=a[lo];

        while(i<=gt){
            int comp=a[i].compareTo(v);   //identical to less method
            if(comp<0){
                swap(a,lt++,i++);
            }else if(comp>0){
                swap(a,i,gt--);
            }else{
                i++;
            }
        }
        sort(a,lo,lt-1);
        sort(a,gt+1,hi);
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
