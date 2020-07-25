package com.Hisham.GeoApplicationofBST.RangeSearch;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

//just modifying the randomized BST for only key
public class RangeSearch<Key extends Comparable<Key>> {

    private Node root;

    private class Node{
        private final Key key;
        private Node left,right;
        private int count;

        public Node(Key key){
            this.key=key;
            this.count=1;
        }
    }

    public boolean isEmpty(){
        return size()==0;
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

    //randomized insertion
    public void put(Key key){
        root=put(root,key);
    }

    // we use the insertion at root algorithm with probability 1/n, and with probability 1-1/n
    // we insert the key recursively into the right or left subtree, according to the respective values of the key and the root.
    private Node put(Node x, Key key){
        if(x==null){
            return new Node(key);
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            x.count=1+size(x.left)+size(x.right);
            return x;
        }
        if(StdRandom.bernoulli(1.0/(size(x)+1.0))){  //it generates true or false based on the success of probability 1/n
            return putRoot(x,key);
        }
        if(comp<0){
            x.left=put(x.left,key);  //tricky part
        }else{
            x.right=put(x.right,key);  //tricky part
        }
        x.count=1+size(x.left)+size(x.right);
        return x;
    }

    private Node putRoot(Node x, Key key){
        if(x==null){
            return new Node(key);
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            return x;
        } else if(comp<0){
            x.left=putRoot(x.left,key);
            x=rotateRight(x);
        }else{
            x.right=putRoot(x.right,key);
            x=rotateLeft(x);
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

    private Node delete(Node x, Key key){
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
    private Node joinLR(Node a, Node b){
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

    public Iterable<Key> keysInOrder(Key lo,Key hi){
        Queue<Key> q=new Queue<>();
        inorder(root,q,lo,hi);
        return q;
    }

    private void inorder(Node x, Queue<Key> q, Key lo, Key hi){
        if(x==null){
            return;
        }
        int complo=lo.compareTo(x.key);
        int comphi=hi.compareTo(x.key);
        if(complo<=0){
            inorder(x.left,q,lo,hi);
        }if(complo<=0 && comphi>=0){
            q.enqueue(x.key);
        }if(comphi>=0){
            inorder(x.right,q,lo,hi);
        }
    }
}
