package com.company.QuickSort;

import edu.princeton.cs.algs4.StdRandom;

import java.security.SecureRandom;

public class QuickSelect {

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

    public static Comparable select(Comparable[] a,int k){ //kth small element

        StdRandom.shuffle(a);
        int lo=0;
        int hi=a.length-1;
        while (hi>lo){
            int j=partition(a,lo,hi);
            if(j==k){
                return a[k];
            }else if(j>k){
                hi=j-1;
            }else {
                lo=j+1;
            }
        }
        return a[k];

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
        System.out.println("kth small :"+select(a,10));
        for(int i=0;i<n;i++){
            System.out.println(a[i]);
        }
    }
}
