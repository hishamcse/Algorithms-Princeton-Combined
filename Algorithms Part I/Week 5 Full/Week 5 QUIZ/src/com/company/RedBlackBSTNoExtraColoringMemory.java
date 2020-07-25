package com.company;

public class RedBlackBSTNoExtraColoringMemory<Key extends Comparable<Key>,Value> {

    //there are various ways to do that.One solution is at the web solution 1.That is we can manipulate count/size function
    //if a node is red,then we can treat the count as negative number.At the case of Black,count is positive
    //and the size method will be modified to return Math.abs(x.count);
    //and the corresponding methods where color was set at the RedBlackBST data type api,we will slightly change them to identify
    //the coloring below is an example how to do that

    private Node root;

    private class Node{
        private Key key;
        private Value value;
        private Node left,right;
        private int count;

        public Node(Key key,Value value){
            this.key=key;
            this.value=value;
        }
    }

    private boolean isRed(Node x){
        if(x==null){
            return false;
        }
        return x.count<0;
    }

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x==null){
            return 0;
        }
        return Math.abs(x.count);
    }

    private Node rotateLeft(Node h){
        if(h==null){
            return null;
        }
        Node x=h.right;
        h.right=x.left;
        x.left=h;
        x.count=h.count;   //as count and color of x will be same as h
        h.count=-Math.abs(1+size(h.left)+size(h.right));   //as h is red
        return x;
    }

    private Node rotateRight(Node h){
        if(h==null){
            return null;
        }
        Node x=h.left;
        h.left=x.right;
        x.right=h;
        x.count=h.count;  //as count and color of x will be same as h
        h.count=-Math.abs(1+size(h.left)+size(h.right));  //as h is red
        return x;
    }

    private void flipColors(Node h){
        if(h==null){
            return;
        }
        h.count=-h.count;
        h.left.count=-h.left.count;
        h.right.count=-h.right.count;
    }

    //in this way,other methods like deleteMin,deleteMax,put etc method will be slightly modified.Not so tough,quite easy.

}
