package com.Hisham.GeoApplicationofBST.RangeSearch;

import edu.princeton.cs.algs4.Queue;

public class BSTEnablingRangeOperation<Key extends Comparable<Key>,Value> {  //full implementation and details at booksite

    private Node root;

    private class Node{
        private final Key key;
        private Value value;
        private Node left,right;
        private int count;

        public Node(Key key,Value value){
            this.key=key;
            this.value=value;
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

    //range search
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

    public void put(Key key,Value value){
        root=put(root,key,value);
    }

    private Node put(Node x,Key key,Value value){
        if(x==null){
            return new Node(key, value);
        }
        int comp=key.compareTo(x.key);
        if(comp<0){
            x.left=put(x.left,key,value);  //tricky part
        }else if(comp>0){
            x.right=put(x.right,key,value);  //tricky part
        }else{
            x.value=value;
        }
        x.count=1+size(x.left)+size(x.right);
        return x;
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

    //hibbard deletion
    public void delete(Key key){
        root=delete(root,key);
    }

    private Node delete(Node x,Key key){
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

    //range search for 1D
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

    //other method implementation at booksite BST.java

}
