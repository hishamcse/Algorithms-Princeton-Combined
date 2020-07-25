package com.company.AnalysisOfAlgorithms;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

public class ThreeSum {
    public static void main(String[] args) {
        int []a=new int[20000];
        for(int i=0;i<20000;i++){
            a[i]= StdRandom.uniform(20000);
        }

        int c=0,key;
        Arrays.sort(a);
        //O(n^3),O(n^2 * logN)
        Stopwatch stopwatch=new Stopwatch();
        for(int i=0;i<20000;i++){
            for(int j=i+1;j<20000;j++){
//                for(int k=2;k<2000;k++){
//                    if(a[i]+a[j]+a[k]==0){
//                        c++;
//                    }
                key=-(a[i]+a[j]);
                //or
                Arrays.binarySearch(a,key);

//                if(binsearch(a,key)!=-1){
//                    c++;
//                }
            }
        }
        System.out.println(c);
        double t=stopwatch.elapsedTime();
        System.out.println(t);
    }
    public static int binsearch(int []a,int key){
        int lo=2;
        int hi=19999;
        int mid;
        while(lo<hi){
            mid=lo+(hi-lo)/2;
            if(key<a[mid]){ hi=mid-1;}
            else if(key>a[mid]){lo=mid+1;}
            else{return mid;}
        }
        return -1;
    }
}
