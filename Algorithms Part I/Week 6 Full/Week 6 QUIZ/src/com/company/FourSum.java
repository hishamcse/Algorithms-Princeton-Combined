package com.company;

import edu.princeton.cs.algs4.StdIn;

import java.util.HashSet;

public class FourSum {

    public static boolean foursum(int[] all){
        if(all.length<4){
            throw new IllegalArgumentException("array length too small");
        }
        int n=all.length;
        HashSet<Integer> hashSet=new HashSet<>();
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                if(hashSet.contains(all[i]+all[j])){
                    System.out.println(all[i]+" "+all[j]);
                    return true;
                }else{
                    hashSet.add(all[i]+all[j]);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] all=new int[10];
        for(int i=0;i<all.length;i++){
            all[i]= StdIn.readInt();
        }
        System.out.println(foursum(all));
    }
}
