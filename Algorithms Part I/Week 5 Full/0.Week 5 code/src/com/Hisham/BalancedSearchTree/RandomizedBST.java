package com.Hisham.BalancedSearchTree;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedBST<Key extends Comparable<Key>,Value> {

    private Node root;

    private class Node{
        private final Key key;
        private Value value;
        private Node left,right;
        private int count;

        public Node(Key key,Value value){
            this.key=key;
            this.value=value;
            this.count=1;
        }
    }

    public boolean isEmpty(){
        return size()==0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
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

    public int size(Key lo,Key hi){
        int comp=lo.compareTo(hi);
        if(comp>0){
            return 0;
        }
        if(contains(hi)){
            return rank(hi)-rank(lo)+1;
        }else{
            return rank(hi)-rank(lo);
        }
    }

    public Value get(Key key){
        Node x=root;
        while (x!=null){
            int comp=key.compareTo(x.key);
            if(comp<0){
                x=x.left;
            }else if(comp>0){
                x=x.right;
            }else{
                return x.value;
            }
        }
        return null;
    }

    //or,
    public Value get2(Key key){
        return get2(root,key);
    }

    private Value get2(Node x,Key key){
        if(x==null){
            return null;
        }
        int comp=key.compareTo(x.key);
        if(comp<0){
            return get2(x.left,key);
        }else if(comp>0){
            return get2(x.right,key);
        }else {
            return x.value;
        }
    }

    //randomized insertion
    public void put(Key key,Value value){
        root=put(root,key,value);
    }

    // we use the insertion at root algorithm with probability 1/n, and with probability 1-1/n
    // we insert the key recursively into the right or left subtree, according to the respective values of the key and the root.
    private Node put(Node x,Key key,Value value){
        if(x==null){
            return new Node(key, value);
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            x.value=value;
            x.count=1+size(x.left)+size(x.right);
            return x;
        }
        if(StdRandom.bernoulli(1.0/(size(x)+1.0))){  //it generates true or false based on the success of probability 1/n
            return putRoot(x,key,value);
        }
        if(comp<0){
            x.left=put(x.left,key,value);  //tricky part
        }else{
            x.right=put(x.right,key,value);  //tricky part
        }
        x.count=1+size(x.left)+size(x.right);
        return x;
    }

    private Node putRoot(Node x,Key key,Value value){
        if(x==null){
            return new Node(key,value);
        }
        int comp=key.compareTo(x.key);
        if(comp<0){
            x.left=putRoot(x.left,key,value);
            x=rotateRight(x);
        }else if(comp>0){
            x.right=putRoot(x.right,key,value);
            x=rotateLeft(x);
        }else{
            x.value=value;
        }
        return x;
    }

    private Node rotateLeft(Node h){
        if(h==null){
            return null;
        }
        Node x=h.right;
        h.right=x.left;
        x.left=h;
        x.count=1+size(x.left)+size(x.right);
        h.count=1+size(h.left)+size(h.right);
        return x;
    }

    private Node rotateRight(Node h){
        if(h==null){
            return null;
        }
        Node x=h.left;
        h.left=x.right;
        x.right=h;
        x.count=1+size(x.left)+size(x.right);
        h.count=1+size(h.left)+size(h.right);
        return x;
    }

    public void delete(Key key){
        root=delete(root,key);
    }

    private Node delete(Node x,Key key){
        if(x==null){
            return null;
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            return joinLR(x.left,x.right);
        } else if(comp<0){
            x.left=delete(x.left,key);
        }else{
            x.right=delete(x.right,key);
        }
        x.count=1+size(x.left)+size(x.right);
        return x;
    }

    //see the paper Randomized BST.pdf for details
    //a=(a,Ll,join(Lr,R))
    //b=(b,join(L,Rl),Rr)
    private Node joinLR(Node a,Node b){
        if(a==null){
            return b;
        }
        if(b==null){
            return a;
        }
        if(StdRandom.bernoulli((double) size(a)/(double) (size(a)+size(b)))){
            a.right=joinLR(a.right,b);
            a.count=1+size(a.left)+size(a.right);
            return a;
        }else{
            b.left=joinLR(a,b.left);
            b.count=1+size(b.left)+size(b.right);
            return b;
        }
    }

    public Key min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left==null){
            return x;
        }else{
            return min(x.left);
        }
    }

    public Key max(){
        return max(root).key;
    }

    private Node max(Node x){
        if(x.right==null){
            return x;
        }else{
            return max(x.right);
        }
    }

    public Key floor(Key key){
        Node x=floor(root,key);
        if(x==null){
            return null;
        }
        return x.key;
    }

    private Node floor(Node x,Key key){
        if(x==null){
            return null;
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            return x;
        }else if(comp<0){
            return floor(x.left,key);
        }
        Node r=floor(x.right,key);
        if(r!=null){
            return r;
        }else{
            return x;
        }
    }

    public Key floor2(Key key){    //keeping the track of best node
        Key xkey=floor2(root,key,null);
        if(xkey==null){
            return null;
        }
        return xkey;
    }

    private Key floor2(Node x,Key key,Key best){
        if(x==null){
            return null;
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            return x.key;
        }else if(comp<0){
            return floor2(x.left,key,best);
        }else{
            return floor2(x.right,key,x.key);
        }
    }

    public Key ceil(Key key){
        Node x=ceil(root,key);
        if(x==null){
            return null;
        }
        return x.key;
    }

    private Node ceil(Node x,Key key){
        if(x==null){
            return null;
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            return x;
        }else if(comp>0){
            return ceil(x.right,key);
        }
        Node l=ceil(x.left,key);
        if(l!=null){
            return l;
        }else{
            return x;
        }
    }

    public Key select(int rank){
        return select(root,rank);
    }

    private Key select(Node x,int rank){
        if(x==null){
            return null;
        }
        int leftsize=size(x.left);
        if(leftsize>rank){
            return select(x.left,rank);
        }else if(leftsize<rank){
            return select(x.right,rank-leftsize-1);
        }else{
            return x.key;
        }
    }

    public void deleteMin(){
        root=deleteMin(root);
    }

    private Node deleteMin(Node x){
        if(x.left==null){
            return x.right;
        }
        x.left=deleteMin(x.left);
        x.count=1+size(x.left)+size(x.right);
        return x;
    }

    public void deleteMax(){
        root=deleteMax(root);
    }

    private Node deleteMax(Node x){
        if(x.right==null){
            return x.left;
        }
        x.right=deleteMax(x.right);
        x.count=1+size(x.left)+size(x.right);
        return x;
    }

    public int rank(Key key){
        return rank(root,key);
    }

    private int rank(Node x,Key key){
        if(x==null){
            return 0;
        }
        int comp=key.compareTo(x.key);
        if(comp<0){
            return rank(x.left,key);
        }else if(comp>0){
            return 1+size(x.left)+rank(x.right,key);
        }else{
            return size(x.left);
        }
    }

    public Iterable<Key> keysInOrder(){
        Queue<Key> q=new Queue<>();
        inorder(root,q);
        return q;
    }

    //inorder traversal of BST at ascending order
    private void inorder(Node x,Queue<Key> q){
        if(x==null){
            return;
        }
        inorder(x.left,q);
        q.enqueue(x.key);
        inorder(x.right,q);
    }

    public Iterable<Key> keysInOrder(Key lo,Key hi){
        Queue<Key> q=new Queue<>();
        inorder(root,q,lo,hi);
        return q;
    }

    private void inorder(Node x,Queue<Key> q,Key lo,Key hi){
        if(x==null){
            return;
        }
        int complo=lo.compareTo(x.key);
        int comphi=hi.compareTo(x.key);
        if(complo<0){
            inorder(x.left,q,lo,hi);
        }else if(complo<=0 && comphi>=0){
            q.enqueue(x.key);
        }else if(comphi>0){
            inorder(x.right,q,lo,hi);
        }
    }
}
