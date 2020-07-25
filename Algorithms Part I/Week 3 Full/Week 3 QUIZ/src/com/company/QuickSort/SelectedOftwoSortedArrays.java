package com.company.QuickSort;

import edu.princeton.cs.algs4.StdRandom;

public class SelectedOftwoSortedArrays {

    /**
    * Credit: https://algorithmsandme.com/find-kth-smallest-element-in-two-sorted-arrays/
    */

    public static int findKthSmallestElement(int[] A, int[] B, int k){

        int[] temp;

        int lenA = A.length;
        int lenB = B.length;

        if(lenA + lenB < k) return -1;

        int iMin = 0;
        int iMax = Integer.min(A.length, k-1);

        int i = 0;
        int j = 0;

        while (iMin <= iMax) {
            i = (iMin + iMax) / 2;
            j = k - 1 - i; // because of zero based index
            if (B[j - 1] > A[i]) {
                // i is too small, must increase it
                iMin = i + 1;
            } else if (i > 0 && A[i - 1] > B[j]) {
                // i is too big, must decrease it
                iMax = i - 1;
            } else {
                // i is perfect
                return Integer.min(A[i], B[j]);
            }
        }
        return -1;
    }

    //part of second solution
    public static int partition(Comparable[] a,int lo,int hi){
        int i=lo;
        int j=hi+1;

        while(true){
            while(less(a[++i],a[lo])){
                if(i==hi){
                    break;
                }
            }
            while (less(a[lo],a[--j])){
                if(j==lo){
                    break;
                }
            }
            if(i>=j){
                break;
            }
            swap(a,i,j);
        }
        swap(a,lo,j);
        return j;
    }

    //another solution(but not exact solution.because time complexity is higher here(NlogN))
    public static Comparable select(Comparable[] a,Comparable[] b,int k){

        StdRandom.shuffle(a);
        StdRandom.shuffle(b);
        int lo=0;
        int hi=a.length-1;

        if(k<=a.length-1) {
            while (hi > lo) {
                int j = partition(a, lo, hi);
                if (j == k) {
                    return a[k];
                } else if (j > k) {
                    hi = j - 1;
                } else {
                    lo = j + 1;
                }
            }
            return a[k];
        }else{
            k=k-a.length;
            while (hi > lo) {
                int j = partition(b, lo, hi);
                if (j == k) {
                    return b[k];
                } else if (j > k) {
                    hi = j - 1;
                } else {
                    lo = j + 1;
                }
            }
            return b[k];
        }
    }

    private static boolean less(Comparable a,Comparable b){
        return a.compareTo(b)<0;
    }

    private static void swap(Comparable[] a,int i,int j){
        Comparable temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }

    public static void main(String[] args) {

        int[] A1 = {1,3,5,6,7,8,9,11};
        int[] A2 = {1,4,6,8,12,14,15,17};

        System.out.println(findKthSmallestElement(A1,A2,10));


    }
}

