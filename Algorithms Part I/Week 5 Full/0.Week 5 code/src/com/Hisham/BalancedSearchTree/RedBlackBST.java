package com.Hisham.BalancedSearchTree;

import edu.princeton.cs.algs4.Queue;

public class RedBlackBST<Key extends Comparable<Key>,Value> {

    private static final boolean RED=true;
    private static final boolean BLACK=false;

    private Node root;

    private class Node{
        private Key key;
        private Value value;
        private Node left,right;
        private boolean color;
        private int count;

        public Node(Key key,Value value,boolean color){
            this.key=key;
            this.value=value;
            this.color=color;
        }
    }

    private boolean isRed(Node x){
        if(x==null){
            return false;
        }
        return x.color==RED;
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

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    public boolean isEmpty(){
        return size()==0;
    }

    public Value get(Key key){
        Node x=get(root,key);
        if(x==null){
            return null;
        }
        return x.value;
    }

    private Node get(Node x,Key key){
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
        x.color=h.color;
        h.color=RED;
        x.count=h.count;
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
        x.color=h.color;
        h.color=RED;
        x.count=h.count;
        h.count=1+size(h.left)+size(h.right);
        return x;
    }

    private void flipColors(Node h){
        if(h==null){
            return;
        }
        h.color=!h.color;
        h.left.color=!h.left.color;
        h.right.color=!h.right.color;
    }

    public void put(Key key,Value value){
        if(value==null){
            delete(key);
            return;
        }
        root=put(root,key,value);
        root.color=BLACK;
    }

    private Node put(Node h,Key key,Value value){
        if(h==null){
            return new Node(key,value,RED);
        }
        int comp=key.compareTo(h.key);
        if(comp==0){
            h.value=value;
        }else if(comp<0){
            h.left=put(h.left,key,value);
        }else{
            h.right=put(h.right,key,value);
        }

        if(isRed(h.right) && !isRed(h.left)){
            h=rotateLeft(h);
        }
        if(isRed(h.left) && isRed(h.left.left)){
            h=rotateRight(h);
        }
        if(isRed(h.right) && isRed(h.left)){
            flipColors(h);
        }
        h.count=1+size(h.left)+size(h.right);
        return h;
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
        }else{
            Node l=ceil(x.left,key);
            if(l!=null){
                return l;
            }else{
                return x;
            }
        }
    }

    public int rank(Key key){
        return rank(root,key);
    }

    private int rank(Node x,Key key){
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

    private Key select(Node x,int rank){
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

    private Node moveRedLeft(Node h){
        flipColors(h);
        if(isRed(h.right.left)){
            h.right=rotateRight(h.right);
            h=rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private Node moveRedRight(Node h){
        flipColors(h);
        if(isRed(h.left.left)){
            h=rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node balance(Node h){
        if(isRed(h.right) && !isRed(h.left)){
            h=rotateLeft(h);
        }
        if(isRed(h.left) && isRed(h.left.left)){
            h=rotateRight(h);
        }
        if(isRed(h.right) && isRed(h.left)){
            flipColors(h);
        }
        h.count=1+size(h.left)+size(h.right);
        return h;
    }

    //for understanding full deletion operation,see algorithms 4th edition book & a downloaded(pdf) paper by robert seidgewick

    public void deleteMin(){
        if(!isRed(root.right) && !isRed(root.left)){
            root.color=RED;
        }
        root=deleteMin(root);
        if(!isEmpty()){
            root.color=BLACK;
        }
    }

    private Node deleteMin(Node h){
        if(h.left==null){
            return null;
        }
        if(!isRed(h.left) && !isRed(h.left.left)){
            h=moveRedLeft(h);
        }
        h.left=deleteMin(h.left);
        return balance(h);
    }

    public void deleteMax(){
        if(!isRed(root.right) && !isRed(root.left)){
            root.color=RED;
        }
        root=deleteMax(root);
        if(!isEmpty()){
            root.color=BLACK;
        }
    }

    private Node deleteMax(Node h){
        if(isRed(h.left)){
            h=rotateRight(h);
        }
        if(h.right==null){
            return null;
        }
        if(!isRed(h.right) && !isRed(h.right.left)){
            h=moveRedRight(h);
        }
        h.right=deleteMax(h.right);
        return balance(h);
    }

    public void delete(Key key){
        if(!contains(key)){
            return;
        }
        if(!isRed(root.right) && !isRed(root.left)){
            root.color=RED;
        }
        root=delete(root,key);
        if(!isEmpty()){
            root.color=BLACK;
        }
    }

    private Node delete(Node h,Key key){
        int comp=key.compareTo(h.key);
        if(comp<0){
            if(!isRed(h.left) && !isRed(h.left.left)){
                h=moveRedLeft(h);
            }
            h.left=delete(h.left,key);
        }else{
            if(isRed(h.left)){
                h=rotateRight(h);
            }
            if(comp==0 && h.right==null){
                return null;
            }
            if(!isRed(h.right) && !isRed(h.right.left)){
                h=moveRedRight(h);
            }
            if(comp==0){
                Node x=min(h.right);
                h.key=x.key;
                h.value=x.value;
                h.right=deleteMin(h.right);
            }else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    public int height(){
        return height(root);
    }

    private int height(Node x){
        if(x==null){
            return -1;
        }
        return 1+Math.max(height(x.left),height(x.right));
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

    //Level Order traversal of BST
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

    //inorder traversal using constant extra space
    public void keysInOrderMorrison(){
        inOrder(root);
    }

    private void inOrder(Node x){
        if(x==null){
            return;
        }
        Node curr=x;
        Node prev;
        while (curr!=null){
            if(curr.left==null){
                System.out.println(curr.key+" "+curr.value);
                curr=curr.right;
            }else{
                prev=curr.left;

                while (prev.right!=null && prev.right!=curr){
                    prev=prev.right;
                }

                if(prev.right==null){
                    prev.right=curr;
                    curr=curr.left;
                }else{
                    prev.right=null;
                    System.out.println(curr.key+" "+curr.value);
                    curr=curr.right;
                }
            }
        }
    }

    public boolean isRedBlackBST(){
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    public boolean isBST(){
        return isBST(root,null,null);
    }

    private boolean isBST(Node x,Key min,Key max){
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

    public boolean is23(){
        return is23(root);
    }

    private boolean is23(Node x){
        if(x==null){
            return true;
        }
        if(isRed(x.right)){
            return false;
        }
        if(x!=root && isRed(x) && isRed(x.left)){
            return false;
        }
        return isRed(x.left) && isRed(x.right);
    }

    public boolean isBalanced(){
        if(root==null){
            return true;
        }
        Node x=root;
        int black=0;
        while (x!=null){
            if(!isRed(x)){
                black++;
            }
            x=x.left;
        }
        return isBalanced(root,black);
    }

    private boolean isBalanced(Node x,int black){
        if(x==null){
            return black==0;
        }
        if(!isRed(x)){
            black--;
        }
        return isBalanced(x.left,black) && isBalanced(x.right,black);
    }
}
