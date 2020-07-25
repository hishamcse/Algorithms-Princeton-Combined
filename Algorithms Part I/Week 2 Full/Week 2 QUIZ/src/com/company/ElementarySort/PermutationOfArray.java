package com.company.ElementarySort;

import java.util.Arrays;

public class PermutationOfArray {

    public static boolean Permutation(int[] a,int[] b){
        Arrays.sort(a);
        Arrays.sort(b);

        if(a.length!=b.length){
            return false;
        }

        for(int i=0;i<a.length;i++){
            if(a[i]!=b[i]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] a = {4, 6, 8, 9, 5};
        int[] b = {4, 6, 5, 9, 8};
        System.out.println(Permutation(a,b));
    }
}
