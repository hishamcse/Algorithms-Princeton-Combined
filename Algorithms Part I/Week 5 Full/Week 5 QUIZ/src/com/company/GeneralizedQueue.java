package com.company;

import edu.princeton.cs.algs4.RedBlackBST;

public class GeneralizedQueue<Key> {

    RedBlackBST<Integer,Key> blackBST;
    private int n;

    public GeneralizedQueue(){
        blackBST=new RedBlackBST<>();
        n=0;
    }

    public void append(Key key){
        blackBST.put(n++,key);
    }

    public void remove(){
        blackBST.deleteMin();
    }

    public Key get(int i){
        return blackBST.get(blackBST.rank(i));
    }

    public void removeparticular(int i){
        blackBST.delete(blackBST.rank(i));
    }

}
