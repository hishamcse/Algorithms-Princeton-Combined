package com.company;

import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class BitonicArray {

    //with finding peak,the whole complexity is ~3logN
    //without finding peak,complexity is ~2logN

    public static int peak(int[] array,int start,int end){
        int mid=(start+end)/2;
        if(start==end){
            return mid;
        } else if(array[mid]<array[mid+1]){
            return peak(array,mid+1,end);
        }else{
            return peak(array,start,mid);
        }
    }

    public static boolean binarysearch(int[] array,int peak,int key,boolean desc){
       if(!desc){
           int lo=0,hi=peak-1;
           while(lo<=hi) {
               int mid = lo + (hi - lo) / 2;
               if(array[mid]<key){lo=mid+1;}
               else if(array[mid]>key){hi=mid-1;}
               else{
                   return true;
               }
           }
       }else {
           int lo=peak+1,hi=array.length-1;
           while(lo<=hi) {
               int mid = lo + (hi - lo) / 2;
               if(array[mid]<key){hi=mid-1;}
               else if(array[mid]>key){lo=mid+1;}
               else{
                   return true;
               }
           }
       }
       return false;
    }

    public static boolean derivedbinarysearch(int[] array,int peak,int key){
        return binarysearch(array,peak,key,true) || binarysearch(array,peak,key,false);
    }

    public static void main(String[] args) {

        int[] a = { 1, 2, 3, 4, 5, 15, 10, 9, 8, 7, 6 };
        int key=StdIn.readInt();

        //first way(~3logN)
        int f=peak(a,0,a.length-1);
        if(a[f]==key) {
            System.out.println("found at " + f);
        }else {
            System.out.println(derivedbinarysearch(a,f,key));
        }

        //second way(~2logN)
        Arrays.sort(a);
        System.out.println(Arrays.binarySearch(a,key));
    }
}
