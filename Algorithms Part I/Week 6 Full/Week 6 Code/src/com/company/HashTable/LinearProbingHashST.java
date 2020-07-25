package com.company.HashTable;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinearProbingHashST<Key,Value> {

    private static final int INIT_CAPACITY=4;

    private int n;
    private int m;
    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int m) {
        this.m = m;
        n=0;
        keys=(Key[]) new Object[m];
        values=(Value[]) new Object[m];
    }

    private void resize(int chains){
        LinearProbingHashST<Key,Value> temp=new LinearProbingHashST<>(chains);
        for(int i=0;i<m;i++){
            if(keys[i]!=null) {
                temp.put(keys[i],values[i]);
            }

        }
        this.m=temp.m;
        this.n=temp.n;
        this.keys=temp.keys;
        this.values=temp.values;
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7ffffff) % m;
    }

    public int size(){
        return n;
    }

    public Value get(Key key){

        for(int i=hash(key);keys[i]!=null;i=(i+1)%m){
            if(keys[i].equals(key)){
                return values[i];
            }
        }
        return null;
    }

    public boolean contains(Key key){
        return get(key)!=null;
    }

    public void put(Key key,Value value){
        if(n>=m/2){
            resize(2*m);
        }
        int i;
        for(i=hash(key);keys[i]!=null;i=(i+1)%m){
            if(keys[i].equals(key)){
                values[i]=value;
                return;
            }
        }
        keys[i]=key;
        values[i]=value;
        n++;
    }

    public void delete(Key key){
        int i;
        for(i=hash(key);keys[i]!=null;i=(i+1)%m){
            if(keys[i].equals(key)){
                break;
            }
        }
        keys[i]=null;
        values[i]=null;

        i=(i+1)%m;
        while (keys[i]!=null){
            Key k=keys[i];
            Value v=values[i];
            keys[i]=null;
            values[i]=null;
            put(k,v);
            i=(i+1)%m;
        }
        n--;

        if(n>0 && n<=m/8){
            resize(m/2);
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            if(keys[i]!=null) {
                queue.enqueue(keys[i]);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
        for (int i = 0; i<20; i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
