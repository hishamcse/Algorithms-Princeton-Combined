package com.company.SymbolTables;

public class InOrderWithConstantSpace<Key extends Comparable<Key>,Value> {  //Morrison Algorithm

    private Node root;

    private class Node{
        private final Key key;
        private final Value value;
        private Node left,right;
        private int count;

        public Node(Key key,Value value){
            this.key=key;
            this.value=value;
        }
    }

    public void inorder(){
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

}
