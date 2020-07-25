package com.Hisham.BalancedSearchTree;

import edu.princeton.cs.algs4.Queue;

public class AVLTreeST<Key extends Comparable<Key>,Value> {

    private Node root;

    private class Node{
        private final Key key;
        private Value value;
        private Node left,right;
        private int count;
        private int height;

        public Node(Key key,Value value,int height,int count){
            this.key=key;
            this.value=value;
            this.height=height;
            this.count=count;
        }
    }

    public int height(){
        return height(root);
    }

    private int height(Node x){
        if(x==null){
            return -1;
        }
        return x.height;
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

    public boolean contains(Key key){
        return get(key)!=null;
    }

    public boolean isEmpty(){
        return root==null;
    }

    public Value get(Key key){
        Node x=get(root,key);
        if(x==null){
            return null;
        }
        return x.value;
    }

    private Node get(Node x, Key key){
        if(x==null){
            return null;
        }
        int comp=key.compareTo(x.key);
        if(comp<0){
            return get(x.left,key);
        }else if(comp>0){
            return get(x.right,key);
        }else{
            return x;
        }
    }

    private Node rotateLeft(Node h){
        if(h==null){
            return null;
        }
        Node x=h.right;
        h.right=x.left;
        x.left=h;
        x.count=h.count;
        h.count=1+size(h.left)+size(h.right);
        x.height=1+Math.max(height(x.left),height(x.right));
        h.height=1+Math.max(height(h.left),height(h.right));
        return x;
    }

    private Node rotateRight(Node h){
        if(h==null){
            return null;
        }
        Node x=h.left;
        h.left=x.right;
        x.right=h;
        x.count=h.count;
        h.count=1+size(h.left)+size(h.right);
        x.height=1+Math.max(height(x.left),height(x.right));
        h.height=1+Math.max(height(h.left),height(h.right));
        return x;
    }

    public void put(Key key,Value value){
        if(value==null){
            delete(key);
            return;
        }
        root=put(root,key,value);
    }

    private Node put(Node h, Key key, Value value){
        if(h==null){
            return new Node(key,value,0,1);
        }
        int comp=key.compareTo(h.key);
        if(comp==0){
            h.value=value;
            return h;
        }else if(comp<0){
            h.left=put(h.left,key,value);
        }else{
            h.right=put(h.right,key,value);
        }
        h.count=1+size(h.left)+size(h.right);
        h.height=1+Math.max(height(h.left),height(h.right));
        return balance(h);
    }

    private Node balance(Node x){
        if(balancefactor(x)<-1){
            if(balancefactor(x.right)>0){
                x.right=rotateRight(x.right);
            }
            x=rotateLeft(x);
        }else if(balancefactor(x)>1){
            if(balancefactor(x.left)<0){
                x.left=rotateLeft(x.left);
            }
            x=rotateRight(x);
        }
        return x;
    }

    private int balancefactor(Node x){
        return height(x.left)-height(x.right);
    }

    public Key min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left==null){
            return x;
        }
        return min(x.left);
    }

    public Key max(){
        return max(root).key;
    }

    private Node max(Node x){
        if(x.right==null){
            return x;
        }
        return max(x.right);
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

    public int rank(Key key){
        return rank(root,key);
    }

    private int rank(Node x, Key key){
        if(x==null){
            return 0;
        }
        int comp=key.compareTo(x.key);
        if(comp==0){
            return size(x.left);
        }else if(comp<0){
            return rank(x.left,key);
        }else{
            return 1+size(x.left)+rank(x.right,key);
        }
    }

    public Key select(int rank){
        return select(root,rank);
    }

    private Key select(Node x, int rank){
        if(x==null){
            return null;
        }
        int leftsize=size(x.left);
        if(leftsize==rank){
            return x.key;
        }else if(leftsize>rank){
            return select(x.left,rank);
        }else{
            return select(x.right,rank-leftsize-1);
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
        x.height=1+Math.max(height(x.left),height(x.right));
        return balance(x);
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
        x.height=1+Math.max(height(x.left),height(x.right));
        return balance(x);
    }

    public void delete(Key key){
        if(!contains(key)){
            return;
        }
        root=delete(root,key);
    }

    //same as bst
    private Node delete(Node x, Key key){
        if(x==null){
            return null;
        }
        int comp=key.compareTo(x.key);
        if(comp<0){
            x.left=delete(x.left,key);
        }else if(comp>0){
            x.right=delete(x.right,key);
        }else{
            if(x.left==null){
                return x.right;
            }
            if(x.right==null){
                return x.left;
            }
            Node t=x;
            x=min(t.right);
            x.right=deleteMin(t.right);
            x.left=t.left;
        }
        x.count=1+size(x.left)+size(x.right);
        x.height=1+Math.max(height(x.left),height(x.right));
        return balance(x);
    }

    public Iterable<Key> keysInOrder(){
        Queue<Key> q=new Queue<>();
        inorder(root,q);
        return q;
    }

    //inorder traversal of BST at ascending order
    private void inorder(Node x, Queue<Key> q){
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

    private void inorder(Node x, Queue<Key> q, Key lo, Key hi){
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

    //Level Order traversal of AVLTree
    public Iterable<Key> keysLevelOrder(){
        Queue<Key> q=new Queue<>();
        Queue<Node> queue=new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()){
            Node x=queue.dequeue();
            if(x==null){
                continue;
            }
            q.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return q;
    }

    public boolean isAVLTreeST(){
        return isAVL() && isBST() && isSizeConsistent() && isRankConsistent();
    }

    public boolean isAVL(){
        return isAVL(root);
    }

    private boolean isAVL(Node x){
        if(x==null){
            return true;
        }
        int bf=balancefactor(x);
        if(bf>1 || bf<-1){
            return false;
        }
        return isAVL(x.left) && isAVL(x.right);
    }

    public boolean isBST(){
        return isBST(root,null,null);
    }

    private boolean isBST(Node x, Key min, Key max){
        if(x==null){
            return true;
        }
        if(min!=null && min.compareTo(x.key)<=0){
            return false;
        }
        if(max!=null && max.compareTo(x.key)>=0){
            return false;
        }
        return isBST(x.left,min,x.key) && isBST(x.right,x.key,max);
    }

    public boolean isSizeConsistent(){
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x){
        if(x==null){
            return true;
        }
        if(x.count!=1+size(x.left)+size(x.right)){
            return false;
        }
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    public boolean isRankConsistent(){
        if(root==null){
            return true;
        }
        for(int i=0;i<size();i++){
            if(i!=rank(select(i))){
                return false;
            }
        }
        for(Key key:keysInOrder()){
            if(key.compareTo(select(rank(key)))!=0){
                return false;
            }
        }
        return true;
    }
}
