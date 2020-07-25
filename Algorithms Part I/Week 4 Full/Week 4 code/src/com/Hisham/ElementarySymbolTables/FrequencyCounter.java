package com.Hisham.ElementarySymbolTables;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;

public class FrequencyCounter {

    public static void main(String[] args) {
        int minlen= StdIn.readInt();
        In in = new In(StdIn.readString());
        ST<String,Integer> st=new ST<>();
        while (!in.isEmpty()){
            String word=in.readString();
            if(word.length()<minlen){
                continue;
            }
            if(!st.contains(word)){
                st.put(word,1);
            }else{
                st.put(word,st.get(word)+1);
            }
        }
        int max=0;
        String res="";
        for(String s:st.keys()){
            if(st.get(s)>=max){
                res=s;
                max=st.get(s);
            }
        }
        System.out.println(res+" "+st.get(res));
    }
}
