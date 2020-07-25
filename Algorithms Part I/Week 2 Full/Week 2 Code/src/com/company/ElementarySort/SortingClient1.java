package com.company.ElementarySort;

import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class SortingClient1 {
    public static void main(String[] args) {
        int n= StdIn.readInt();
        Integer[] a=new Integer[n];
        File file=new File(StdIn.readString());
        File[] files=file.listFiles();
        for(int i=0;i<n;i++){
            a[i]= StdRandom.uniform(n);
        }
        Instant instant=Instant.now();
//        Arrays.sort(a);   //faster than insertion sort
        assert files != null;
        Arrays.sort(files);

        Insertion.sort(a);
        Insertion.sort(files);

        Instant instant1=Instant.now();
        System.out.println("time ="+ Duration.between(instant,instant1).toMillis());
        for(int i=0;i<n;i++){
            System.out.println(a[i]);
        }
    }
}
