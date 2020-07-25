package com.Hisham.PriorityQueues;

import edu.princeton.cs.algs4.StdOut;

public class UnorderedArrayImplementation<Key extends Comparable<Key>> {

     private Key[] pq;
     private int n;

     public UnorderedArrayImplementation(int capacity){
         pq=(Key[]) new Comparable[capacity];
         n=0;
     }

     private boolean less(int a,int b){
        return pq[a].compareTo(pq[b])<0;
     }

     private void swap(int i,int j){
        Key temp=pq[i];
        pq[i]=pq[j];
        pq[j]=temp;
     }

     public boolean isEmpty(){
         return n==0;
     }

     public void insert(Key item){
         pq[n++]=item;
     }

     public Key delMAX(){
         int max=0;
         for(int i=1;i<n;i++){
             if(less(max,i)){
                 max=i;
             }
         }
         swap(max,n-1);
         return pq[--n];
     }

    public static void main(String[] args) {
        UnorderedArrayImplementation<String> pq = new UnorderedArrayImplementation<>(10);
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        while (!pq.isEmpty())
            StdOut.println(pq.delMAX());
    }

}
