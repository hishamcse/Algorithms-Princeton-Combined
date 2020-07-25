package com.company.QuickSort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;
import java.util.List;

public class DecimalDominant {

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

    public static List<Comparable> decimaldominant(Comparable[] a,int s){

        int n=a.length;
        List<Comparable> list=new LinkedList<>();
        Comparable[] all=new Comparable[s];
        StdRandom.shuffle(a);

        //o(n*k)
        for(int i=1;i<=s;i++){
            all[i-1]=select(a,i*(n-1)/s);
        }

        int k;

        //o(n*k)
        for(int i=0;i<s;i++) {
            k=0;
            for (int j = 0; j < n; j++) {
                if(all[i].compareTo(a[j])==0){
                    k++;
                    if(k>(n/s) && !list.contains(all[i])){
                        list.add(all[i]);
                        break;
                    }
                }
            }
        }

        return list;
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
        Integer[] a={1,2,3,4,5,6,7,8,9,10,11,2,6,12,14,15,89,11,23,29,34,38,42,11};
        List<Comparable> all = new LinkedList<>(decimaldominant(a, 10));

        for(Comparable i:all){
            System.out.println(i);
        }
    }
}
