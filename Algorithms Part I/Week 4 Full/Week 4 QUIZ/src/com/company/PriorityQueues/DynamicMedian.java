package com.company.PriorityQueues;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class DynamicMedian<Key> {
//    private MaxPQ<Integer> maxpq=new MaxPQ<>();
//    private MinPQ<Integer> minpq=new MinPQ<>();
//
//    public void insert(int key){
//        int med=median();
//        int l=maxpq.size();
//        int r=minpq.size();
//        if(key>=med){
//            minpq.insert(key);
//            if(r-l>1){
//                maxpq.insert(minpq.delMin());
//            }
//        }else {
//            maxpq.insert(key);
//            if(l-r>1){
//                minpq.insert(maxpq.delMax());
//            }
//        }
//    }
//
//    public int median(){
//        int l=maxpq.size();
//        int r=minpq.size();
//        if(l!=0 || r!=0) {
//            if (l == r) {
//                return (maxpq.max() + minpq.min()) / 2;
//            } else if (l > r) {
//                return maxpq.max();
//            } else {
//                return minpq.min();
//            }
//        }else {
//            return 0;
//        }
//    }
//
//    public int delMedian(){
//        int l=maxpq.size();
//        int r=minpq.size();
//        if(l==r){
//            if(maxpq.max()<minpq.min()){
//                return maxpq.delMax();
//            }else{
//                return minpq.delMin();
//            }
//        } else if(l>r){
//            return maxpq.delMax();
//        } else{
//            return minpq.delMin();
//        }
//    }
//
//    public static void main(String[] args) {
//        int n= StdIn.readInt();
//        DynamicMedian<Integer> dynamicMedian=new DynamicMedian<>();
//        for(int i=0;i<n;i++){
//            dynamicMedian.insert(StdIn.readInt());
//        }
//        System.out.println(dynamicMedian.median());
//        System.out.println(dynamicMedian.delMedian());
//    }

    //solution 2(using java's library)
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    public void insert(int e) {
        if (maxHeap.isEmpty() || e <= maxHeap.peek()) {
            maxHeap.offer(e);
        } else {
            minHeap.offer(e);
        }
        balance();
    }

    private void balance() {
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        }
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public int median() {
        if (maxHeap.isEmpty()) {
            throw new NoSuchElementException("no more element");
        }
        return maxHeap.peek();
    }

    public int delMedian() {
        if (maxHeap.isEmpty()) {
            throw new NoSuchElementException("no more element");
        }
        int ret = maxHeap.poll();
        balance();
        return ret;
    }
}
