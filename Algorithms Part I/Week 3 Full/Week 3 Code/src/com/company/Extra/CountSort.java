package com.company.Extra;

import java.util.Scanner;

public class CountSort {

    public static void countsort(int[] a){  //suppose we know that the elements range from 0 to 3
        int m=4;
        int[] count=new int[m];
        for(int i=0;i<m;i++){
            count[i]=0;
        }
        for(int i=0;i<a.length;i++){
            count[a[i]]++;
        }
        int[] pos=new int[m];
        for(int i=0;i<m;i++){
            pos[i]=0;
        }
        for(int i=1;i<m;i++){
            pos[i]=pos[i-1]+count[i-1];
        }
        int[] newA=new int[a.length];
        for(int i=0;i<a.length;i++){
            newA[pos[a[i]]]=a[i];
            pos[a[i]]++;
        }
        for(int i=0;i<newA.length;i++){
            System.out.print(newA[i]+" ");
        }
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int[] a=new int[10];
        for(int i=0;i<10;i++){
            a[i]=scanner.nextInt();
        }
        countsort(a);
    }
}
