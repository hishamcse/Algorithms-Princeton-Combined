package com.company.QuickSort;

import edu.princeton.cs.algs4.Inversions;

import java.security.SecureRandom;
import java.util.Scanner;

public class KendallTau {

    public static long KendallTauDistance(Comparable[] a) {
        return Inversions.count(a);
    }

    //    public static void main(String[] args) {
//        SecureRandom random=new SecureRandom();
//        int n= random.nextInt(1000);
//        System.out.println(n);
//        Integer []a=new Integer[n];
//        for(int i=0;i<n;i++){
//            a[i]= random.nextInt();
//        }
//        long k=KendallTauDistance(a);
//        System.out.println(k);
//    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        System.out.println(KendallTauDistance(a));
    }

}
