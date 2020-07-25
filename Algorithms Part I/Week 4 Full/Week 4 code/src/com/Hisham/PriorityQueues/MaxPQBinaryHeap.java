package com.Hisham.PriorityQueues;

public class MaxPQBinaryHeap<Key extends Comparable<Key>> {

    private final Key[] maxpq;
    private int n;

    public MaxPQBinaryHeap(int capacity){
        maxpq=(Key[]) new Comparable[capacity+1];
        n=0;
    }

    public boolean isEmpty(){
        return n==0;
    }

    private void swim(int k){
        while (k>n && less(k/2,k)){
            swap(k,k/2);
            k=k/2;
        }
    }

    private void sink(int k){
        while (2*k<=n){
            int j=2*k;
            if(j<n && less(j,j+1)){
                j++;
            }
            if(!less(k,j)){
                break;
            }
            swap(k,j);
            k=j;
        }
    }

    public void insert(Key item){
        maxpq[++n]=item;
        swim(n);
    }

    public Key delMAX(){
        Key item=maxpq[1];
        swap(1,n--);
        sink(1);
        maxpq[n+1]=null;
        return item;
    }

    private boolean less(int i,int j){
        return maxpq[i].compareTo(maxpq[j])<0;
    }

    private void swap(int i,int j){
        Key temp=maxpq[i];
        maxpq[i]=maxpq[j];
        maxpq[j]=temp;
    }
}
