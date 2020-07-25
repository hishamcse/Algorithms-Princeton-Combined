package com.company.ElementarySort;

import java.util.Arrays;

public class InsertionofTwoArray {

    public static int function(int[] a,int[] b){
        Arrays.sort(a);
        Arrays.sort(b);

        int c=0,i=0,j=0;
        while (i<a.length && j<b.length) {
            if(a[i]==b[j]){
                c++;
                i++;
                j++;
            }else if(a[i]<b[j]){
                i++;
            }else if(a[i]>b[j]){
                j++;
            }
        }
        return c;
    }

    public static void main(String[] args) {
        int[] a = {3, 2, 1, 0, 9, 8};
        int[] b = {4, 6, 5, 9, 8};
        System.out.println(InsertionofTwoArray.function(a,b));
    }
}
