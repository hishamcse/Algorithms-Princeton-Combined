package com.company.SearchingApplication;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;

public class LookUpCSV {

    public static void main(String[] args) {
        In in=new In(StdIn.readString());
        int keyfield=StdIn.readInt();
        int valfield=StdIn.readInt();
        ST<String,String> st=new ST<>();

        while (!in.isEmpty()){
            String s=in.readLine();
            String[] str=s.split(",");
            st.put(str[keyfield],str[valfield]);
        }

        while (!StdIn.isEmpty()){
            String s=StdIn.readString();
            if(!st.contains(s)){
                System.out.println("not found");
            }else{
                System.out.println(st.get(s));
            }
        }
    }
}
