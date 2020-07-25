package com.company;

import edu.princeton.cs.algs4.BST;

public class DocumentSearch<Key extends Comparable<Key>,Value> {  //no duplicates

    private BST<Key, Integer> bst=new BST<>();

    public DocumentSearch(Key[] words){
        for(int i=0;i<words.length;i++){
            if(!bst.contains(words[i])){
                bst.put(words[i],1);
            }else{
                bst.put(words[i],bst.get(words[i])+1);
            }
        }
    }

    public int shortestInterval(Key[] queries){
        int min=bst.size();
        int max=0;
        for(int i=0;i<queries.length;i++){
            int rank=bst.rank(queries[i]);
            if(rank<min){
                min=rank;
            }
            if(rank>max){
                max=rank;
            }
        }
        return bst.size(bst.select(min),bst.select(max));
    }

    public static void main(String[] args) {

        //test 1(output 4)
        DocumentSearch documentSearch=new DocumentSearch(new String[]{"a", "b","c","d","e","b","d","d","e"});
        System.out.println(documentSearch.shortestInterval(new String[]{"b","d","e"}));

        //test 2(output 4)
        DocumentSearch documentSearch1=new DocumentSearch(new String[]{"h","p","s","r","h","g","o","g","e","z",
                "y","f","r","w","f","r","e","j","y","t","j","k","z","v","g","p","j","n","q","i","l"});
        System.out.println(documentSearch1.shortestInterval(new String[]{"g","j"}));

    }

}
