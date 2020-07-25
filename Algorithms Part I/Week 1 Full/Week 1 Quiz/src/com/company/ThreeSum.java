package com.company;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.Arrays;

public class ThreeSum {

    //O(n^2)
    public static void main(String[] args) {
        int n= StdIn.readInt();
        int []a=new int[n];
        for(int i=0;i<n;i++){
            a[i]= StdRandom.uniform(2000);
        }

        int c=0;
        int j,k,s;
        Stopwatch stopwatch=new Stopwatch();
        Arrays.sort(a);
        for(int i=0;i<n-2;i++){
            j=i+1;
            k=n-1;
            while(j<k){
                s=a[j]+a[k]+a[i];
                if(s==0){c++;}
                if(s>=0){
                    k--;
                }else{
                    j++;
                }
            }
        }
        System.out.println(c);
        double t=stopwatch.elapsedTime();
        System.out.println("time= "+t);
    }
}
