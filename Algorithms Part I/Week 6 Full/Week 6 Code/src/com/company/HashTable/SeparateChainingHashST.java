package com.company.HashTable;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.lang.instrument.Instrumentation;

public class SeparateChainingHashST<Key, Value> {

     private static final int INIT_CAPACITY=4;

     private int n=0;
     private int m;
     private SequentialSearchST<Key, Value>[] st;

     public SeparateChainingHashST() {
        this(INIT_CAPACITY);
     }

     public SeparateChainingHashST(int m) {
        this.m = m;
        st=(SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for(int i=0;i<this.m;i++){
            st[i]=new SequentialSearchST<>();
        }
     }

     private void resize(int chains){
         SeparateChainingHashST<Key,Value> temp=new SeparateChainingHashST<>(chains);
         for(int i=0;i<m;i++){
             for(Key key:st[i].keys()){
                 temp.put(key,st[i].get(key));
             }
         }
         this.m=temp.m;
         this.n=temp.n;
         this.st=temp.st;
     }

     private int hash(Key key){
         return (key.hashCode() & 0x7ffffff) % m;
     }

     public int size(){
         return n;
     }

     public Value get(Key key){
         int i=hash(key);
         return st[i].get(key);
     }

     public boolean contains(Key key){
         return get(key)!=null;
     }

     public void put(Key key,Value value){
         if(n>=m*10){
             resize(2*m);
         }
         int i=hash(key);
         if(st[i].contains(key)){
             st[i].delete(key);
         }else{
             n++;
         }
         st[i].put(key,value);
     }

     public void delete(Key key){
         int i=hash(key);
         if(st[i].contains(key)){
             n--;
         }
         st[i].delete(key);

         if(n>INIT_CAPACITY && n<=2*m){
             resize(m/2);
         }
     }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        for (int i = 0; i<10; i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }

}
