package com.company.SearchingApplication;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;

public class Concordance {

    public static void main(String[] args) {
        ST<String, SET<Integer>> st=new ST<>();
        In in=new In(StdIn.readString());
        String[] all=in.readAll().split(" ");
        for(int i=0;i<all.length;i++){
            String key=all[i];
            if(!st.contains(key)){
                st.put(key,new SET<>());
            }
            SET<Integer> set=st.get(key);
            set.add(i);
        }
        while (!StdIn.isEmpty()){
            String query=StdIn.readString();
            SET<Integer> set=st.get(query);
            for(int i:set){
                //print out [i-4] to [i+4];
            }
        }
    }
}
