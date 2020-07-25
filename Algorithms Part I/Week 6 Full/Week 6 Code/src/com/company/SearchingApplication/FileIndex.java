package com.company.SearchingApplication;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;

import java.io.File;

public class FileIndex {

    public static void main(String[] args) {

        ST<String, SET<File>> st=new ST<>();
        int n= StdIn.readInt();
        for(int i=0;i<n;i++){
            File file=new File(StdIn.readString());
            In in=new In(file);
            while (!in.isEmpty()){
                String key=in.readString();
                if(!st.contains(key)){
                    st.put(key,new SET<>());
                }
                SET<File> files=st.get(key);
                files.add(file);
            }
        }
        while (!StdIn.isEmpty()){
            String s=StdIn.readString();
            System.out.println(st.get(s));
        }

    }
}
