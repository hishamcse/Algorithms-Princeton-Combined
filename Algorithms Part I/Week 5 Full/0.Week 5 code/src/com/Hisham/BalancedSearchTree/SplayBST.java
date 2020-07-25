package com.Hisham.BalancedSearchTree;

public class SplayBST<Key extends Comparable<Key>,Value> {
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

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x==null){
            return 0;
        }
        return x.count;
    }

    private Node rotateLeft(Node h){
        Node x=h.right;
        h.right=x.left;
        x.left=h;
        return x;
    }

    private Node rotateRight(Node h){
        Node x=h.left;
        h.left=x.right;
        x.right=h;
        return x;
    }

    private Node splay(Node h,Key key){
        if(h==null){
            return null;
        }
        int comp=key.compareTo(h.key);
        if(comp<0){
            if(h.left==null){
                return h;
            }
            int comp2=key.compareTo(h.left.key);
            if(comp2<0){
                h.left.left=splay(h.left.left,key);
                h=rotateRight(h);
            }else if(comp2>0){
                h.left.right=splay(h.left.right,key);
                if(h.left.right!=null){
                    h.left=rotateLeft(h.left);
                }
            }
            if(h.left==null){
                return h;
            }else {
                return rotateRight(h);
            }
        }else if(comp>0){
            if(h.right==null){
                return h;
            }
            int comp2=key.compareTo(h.right.key);
            if(comp2<0){
                h.right.left=splay(h.right.left,key);
                if(h.right.left!=null){
                    h.right=rotateRight(h.right);
                }
            }else if(comp2>0){
                h.right.right=splay(h.right.right,key);
                h=rotateLeft(h);
            }
            if(h.right==null){
                return h;
            }else{
                return rotateLeft(h);
            }
        }
        return h;
    }

    public Value get(Key key){
        root=splay(root,key);
        int comp=key.compareTo(root.key);
        if(comp==0){
            return root.value;
        }
        return null;
    }

    public void put(Key key,Value value){
        if(root==null){
            root=new Node(key,value);
            return;
        }
        root=splay(root,key);
        int comp=key.compareTo(root.key);
        if(comp<0){
            Node n=new Node(key,value);
            n.left=root.left;
            n.right=root;
            root.left=null;
            root=n;
        }else if(comp>0){
            Node n=new Node(key,value);
            n.right=root.right;
            n.left=root;
            root.right=null;
            root=n;
        }else{
            root.value=value;
        }
    }

    public void delete(Key key){
        root=splay(root,key);
        int comp=key.compareTo(root.key);
        if(comp==0){
            if(root.left==null){
                root=root.right;
            }else{
                Node x=root.right;
                root=root.left;
                splay(root,key);
                root.right=x;
            }
        }
    }
}
