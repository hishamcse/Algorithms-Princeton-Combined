package com.Hisham.ElementarySymbolTables;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;

public class BasicTestClient {

    public static void main(String[] args) {  //will not maintain serial
        int n= StdIn.readInt();
        ST<String,Integer> st=new ST<>();
        for(int i=0;i<n;i++){
            String s=StdIn.readString();
            st.put(s,i);
        }
        st.delete("hello");
        for(String s:st.keys()){
            System.out.println(s+" "+st.get(s));
        }
    }
}
