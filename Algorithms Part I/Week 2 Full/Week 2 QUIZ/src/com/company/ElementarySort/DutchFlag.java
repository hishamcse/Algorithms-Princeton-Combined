package com.company.ElementarySort;

import java.util.Arrays;

public class DutchFlag {

    public static String[] sortcolor(String[] a){
        int n=a.length;
        int[]b;

        b=setcolor(a);

        Arrays.sort(b);

        return retrievecolor(b);
    }

    private static int[] setcolor(String[] a){
        int n=a.length;
        int[] b=new int[n];

        for(int i=0;i<n;i++){
            if(a[i].equals("Red")){
                b[i]=0;
            }else if(a[i].equals("White")){
                b[i]=1;
            }else{
                b[i]=2;
            }
        }
        return b;
    }

    private static String[] retrievecolor(int[] b){
        int n=b.length;
        String[] a=new String[n];

        for(int i=0;i<n;i++){
            if(b[i]==0){
                a[i]="Red";
            }else if(b[i]==1){
                a[i]="White";
            }else{
                a[i]="Blue";
            }
        }
        return a;
    }

    public static void main(String[] args) {
        String[] a={"Red","Blue","Blue","Red","White"};
        a=DutchFlag.sortcolor(a);

        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }
}
