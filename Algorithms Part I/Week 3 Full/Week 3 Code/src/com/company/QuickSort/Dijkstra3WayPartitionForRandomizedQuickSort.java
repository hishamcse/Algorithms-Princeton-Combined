package com.company.QuickSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public class Dijkstra3WayPartitionForRandomizedQuickSort {

    private static final Random random = new Random();

    public static int[] partition3(int[] a,int lo,int hi){  //replacable with comparable

        if(hi<=lo){
            return null;
        }

        int lt=lo;
        int gt=hi;
        int i=lo;
        int v=a[lo];

        while(i<=gt){
            int comp=Integer.compare(a[i],v);   //identical to less method
            if(comp<0){
                swap(a,lt++,i++);
            }else if(comp>0){
                swap(a,i,gt--);
            }else{
                i++;
            }
        }
        int m1=lt;
        int m2=gt;
        return new int[]{m1, m2};
    }

    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        swap(a,l,k);
        //use partition3
        int[] m = partition3(a, l, r);
        assert m != null;
        randomizedQuickSort(a, l, m[0] - 1);   //lt-1
        randomizedQuickSort(a, m[m.length-1] + 1, r);  //gt+1
    }

    private static void swap(int[] a,int i,int j){
        int temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
//        int n=random.nextInt(100000);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
//            a[i]=random.nextInt(1000000000);
        }
        randomizedQuickSort(a, 0, n - 1);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}


