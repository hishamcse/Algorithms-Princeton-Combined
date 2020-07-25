package com.company.SymbolTables;

public class IsBST<Key extends Comparable<Key>,Value> {  //actually it is a part iof the api BST.java at booksite

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

    public boolean isBST(){
        return isBST(root,null,null);
    }

    private boolean isBST(Node x,Key min,Key max){
        if(x==null){
            return true;
        }
        if(min!=null && min.compareTo(x.key)>=0){
            return false;
        }
        if(max!=null && max.compareTo(x.key)<=0){
            return false;
        }
        return isBST(x.left,min,x.key) && isBST(x.right,x.key,max);
    }
}
