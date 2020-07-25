package com.Hisham.PriorityQueues;

public class MinPQBinaryHeap<Key extends Comparable<Key>> {
    private final Key[] minpq;
    private int n;

    public MinPQBinaryHeap(int capacity){
        minpq=(Key[]) new Comparable[capacity+1];
        n=0;
    }

    public boolean isEmpty(){
        return n==0;
    }

    private void swim(int k){
        while (k>n && greater(k/2,k)){
            swap(k,k/2);
            k=k/2;
        }
    }

    private void sink(int k){
        while (2*k<=n){
            int j=2*k;
            if(j<n && greater(j,j+1)){
                j++;
            }
            if(!greater(k,j)){
                break;
            }
            swap(k,j);
            k=j;
        }
    }

    public void insert(Key item){
        minpq[++n]=item;
        swim(n);
    }

    public Key delMIN(){
        Key item=minpq[1];
        swap(1,n--);
        sink(1);
        minpq[n+1]=null;
        return item;
    }

    private boolean greater(int i,int j){
        return minpq[i].compareTo(minpq[j])>0;
    }

    private void swap(int i,int j){
        Key temp=minpq[i];
        minpq[i]=minpq[j];
        minpq[j]=temp;
    }
}
