package com.Hisham.GeoApplicationofBST.Interval1DSearch;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.LinkedList;
import java.util.List;

//implementation of randomized BST using Interval1D 
public class IntervalST<Value> {

    private Node root;

    private class Node{
        private final Interval1D interval;
        private Value value;
        private Node left,right;
        private int count;
        private int max;

        public Node(Interval1D interval,Value value){
            this.interval=interval;
            this.value=value;
            this.count=1;
            this.max=interval.max;
        }
    }

    public boolean isEmpty(){
        return size()==0;
    }

    public boolean contains(Interval1D interval) {
        if (interval == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(interval) != null;
    }

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x==null){
            return 0;
        }
        return x.count;
    }

    public Value get(Interval1D interval){
        return get(root,interval);
    }

    private Value get(Node x, Interval1D interval){
        if(x==null){
            return null;
        }
        int comp=interval.compareTo(x.interval);
        if(comp<0){
            return get(x.left,interval);
        }else if(comp>0){
            return get(x.right,interval);
        }else {
            return x.value;
        }
    }

    //randomized insertion
    public void put(Interval1D interval,Value value){
        if(contains(interval)){
            delete(interval);
        }
        root=put(root,interval,value);
    }

    // we use the insertion at root algorithm with probability 1/n, and with probability 1-1/n
    // we insert the interval recursively into the right or left subtree, according to the respective values of the interval and the root.
    private Node put(Node x, Interval1D interval, Value value){
        if(x==null){
            return new Node(interval, value);
        }
        int comp=interval.compareTo(x.interval);
        if(comp==0){
            x.value=value;
            fix(x);
            return x;
        }
        if(StdRandom.bernoulli(1.0/(size(x)+1.0))){  //it generates true or false based on the success of probability 1/n
            return putRoot(x,interval,value);
        }
        if(comp<0){
            x.left=put(x.left,interval,value);  //tricky part
        }else{
            x.right=put(x.right,interval,value);  //tricky part
        }
        fix(x);
        return x;
    }

    private Node putRoot(Node x, Interval1D interval, Value value){
        if(x==null){
            return new Node(interval,value);
        }
        int comp=interval.compareTo(x.interval);
        if(comp==0){
            x.value=value;
            return x;
        }else if(comp<0){
            x.left=putRoot(x.left,interval,value);
            x=rotateRight(x);
        }else{
            x.right=putRoot(x.right,interval,value);
            x=rotateLeft(x);
        }
        return x;
    }

    private void fix(Node x){
        if(x==null){
            return;
        }
        x.count=1+size(x.left)+size(x.right);
        x.max=max3(x.interval.max,max(x.left),max(x.right));
    }

    private int max(Node x) {
        if (x == null) return Integer.MIN_VALUE;
        return x.max;
    }

    // precondition: a is not null
    private int max3(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    private Node rotateLeft(Node h){
        Node x=h.right;
        h.right=x.left;
        x.left=h;
        fix(x);
        fix(h);
        return x;
    }

    private Node rotateRight(Node h){
        Node x=h.left;
        h.left=x.right;
        x.right=h;
        fix(x);
        fix(h);
        return x;
    }

    public void delete(Interval1D interval){
        root=delete(root,interval);
    }

    private Node delete(Node x, Interval1D interval){
        if(x==null){
            return null;
        }
        int comp=interval.compareTo(x.interval);
        if(comp==0){
            x=joinLR(x.left,x.right);
        } else if(comp<0){
            x.left=delete(x.left,interval);
        }else{
            x.right=delete(x.right,interval);
        }
        fix(x);
        return x;
    }

    //see the paper Randomized BST.pdf for details
    //a=(a,Ll,join(Lr,R))
    //b=(b,join(L,Rl),Rr)
    private Node joinLR(Node a, Node b){
        if(a==null){
            return b;
        }
        if(b==null){
            return a;
        }
        if(StdRandom.bernoulli((double) size(a)/(double) (size(a)+size(b)))){
            a.right=joinLR(a.right,b);
            fix(a);
            return a;
        }else{
            b.left=joinLR(a,b.left);
            fix(b);
            return b;
        }
    }

    public Interval1D search(Interval1D interval){
        return search(root,interval);
    }

    private Interval1D search(Node x,Interval1D interval){
        if(x==null){
            return null;
        }
        boolean intersection=interval.intersects(x.interval);
        if(intersection){
            return x.interval;
        }else if(x.left==null){
            return search(x.right,interval);
        }else if(interval.min>x.left.max){
            return search(x.right,interval);
        }else{
            return search(x.left,interval);
        }
    }

    public Iterable<Interval1D> searchAll(Interval1D interval){
        List<Interval1D> list=new LinkedList<>();
        searchAll(root,interval,list);
        return list;
    }

    private void searchAll(Node x,Interval1D interval,List<Interval1D> list){
        if(x==null){
            return;
        }
        boolean intersection=interval.intersects(x.interval);
        if(intersection){
            list.add(x.interval);
        }
        if(x.left==null){
            searchAll(x.right,interval,list);
        }
        if(x.left!=null && interval.min>x.left.max){
            searchAll(x.right,interval,list);
        }
        if(x.left!=null && interval.min<=x.left.max){
            searchAll(x.left,interval,list);
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(StdIn.readString());

        // generate N random intervals and insert into data structure
        IntervalST<String> st = new IntervalST<>();
        for (int i = 0; i < N; i++) {
            int min = (int) (Math.random() * 1000);
            int max = (int) (Math.random() * 50) + min;
            Interval1D interval = new Interval1D(min, max);
            StdOut.println(interval);
            st.put(interval, "" + i);
        }

        // print out tree statistics
        System.out.println("size: " + st.size());
        System.out.println();

        System.out.println("generate random intervals and check for overlap");
        // generate random intervals and check for overlap
        for (int i = 0; i < N; i++) {
            int min = (int) (Math.random() * 100);
            int max = (int) (Math.random() * 10) + min;
            Interval1D interval = new Interval1D(min, max);
            StdOut.println(interval + ":  " + st.search(interval));
            StdOut.print(interval + ":  ");
            for (Interval1D x : st.searchAll(interval))
                StdOut.print(x + " ");
            StdOut.println();
            StdOut.println();
        }
    }
    
}
