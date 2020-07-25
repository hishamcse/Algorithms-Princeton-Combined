package com.company.HashTable;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SeparateLiteChainingHashST<Key,Value> {

    private final int M;       //number of chains
    private final Node[] st;  //array of chains(Linked list symbol table)

    public SeparateLiteChainingHashST(int i) {
        M=i;
        st=new Node[M];
    }

    private static class Node{
        private final Object key;
        private Object val;
        private final Node next;

        public Node(Object key,Object value,Node next){
            this.key=key;
            this.val=value;
            this.next=next;
        }
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7ffffff) % M;
    }

    public Value get(Key key){
        int i=hash(key);
        for(Node x=st[i];x!=null;x=x.next){
            if(key.equals(x.key)){
                return (Value) x.val;
            }
        }
        return null;
    }

    public void put(Key key,Value value){
        int i=hash(key);
        for(Node x=st[i];x!=null;x=x.next){
            if(key.equals(x.key)){
                x.val= value;
                return;
            }
        }
        st[i]=new Node(key,value,st[i]);
    }

    public Iterable<Key> keys()  {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            for (Node x = st[i]; x != null; x = x.next) {
                queue.enqueue((Key) x.key);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        SeparateLiteChainingHashST<String, Integer> st = new SeparateLiteChainingHashST<String, Integer>(97);
        for (int i = 0; i<97; i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));

    }
}
