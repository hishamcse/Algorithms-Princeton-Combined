package com.Hisham.BalancedSearchTree;

public class BTree<Key extends Comparable<Key>,Value> {

    private static final int M=4;

    private Node root;
    private int height;
    private int n;   //number of key,value pairs in btree

    private static class Node{
        private int m;   // number of children
        private final Entry[] children=new Entry[m];

        public Node(int m) {
            this.m = m;
        }
    }

    private static class Entry{
        private Comparable key;
        private final Object value;
        private Node next;

        public Entry(Comparable key, Object value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public int size(){
        return n;
    }

    public boolean isEmpty(){
        return size()==0;
    }

    public int height(){
        return height;
    }

    public Value get(Key key){
        return search(root,key,height);
    }

    private Value search(Node x,Key key,int ht){
        Entry[] children=x.children;

        //external node
        if(ht==0){
            for(int j=0;j<x.m;j++){
                if(eq(key,children[j].key)){
                    return (Value) children[j].value;
                }
            }
        } else{   //internal node
            for(int j=0;j<x.m;j++){
                if(j+1==x.m || less(key,children[j+1].key)){
                    return search(children[j].next,key,ht-1);
                }
            }
        }
        return null;
    }

    public void put(Key key,Value value){
        Node u=insert(root,key,value,height);
        if(u==null){
            return;
        }
        Node t=new Node(2);
        t.children[0]=new Entry(root.children[0].key,null,root);
        t.children[1]=new Entry(u.children[0].key,null,u);
        root=t;
        height++;
    }

    private Node insert(Node h,Key key,Value value,int ht){
        int j;
        Entry t=new Entry(key,value,null);
        if(ht==0){
            for(j=0;j<h.m;j++){
                if(less(key,h.children[j].key)){
                    break;
                }
            }
        }else{
            for(j=0;j<h.m;j++) {
                if(j+1==h.m || less(key,h.children[j+1].key)){
                    Node u=insert(h.children[j++].next,key,value,ht-1);
                    if(u==null){
                        return null;
                    }
                    t.key=u.children[0].key;
                    t.next=u;
                    break;
                }
            }
        }
        for(int i=h.m;i>j;i--){
            h.children[i]=h.children[i-1];
        }
        h.children[j]=t;
        h.m++;
        if(h.m<M){
            return null;
        }else{
            return split(h);
        }
    }

    private Node split(Node h){
        Node x=new Node(M/2);
        h.m=M/2;
        for(int i=0;i<M/2;i++){
            x.children[i]=h.children[M/2+i];
        }
        return x;
    }

    private boolean eq(Comparable k1,Comparable k2){
        return k1.compareTo(k2)==0;
    }

    private boolean less(Comparable k1,Comparable k2){
        return k1.compareTo(k2)<0;
    }

}
