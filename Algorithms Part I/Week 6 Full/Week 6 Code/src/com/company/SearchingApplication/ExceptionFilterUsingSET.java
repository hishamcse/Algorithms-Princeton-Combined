package com.company.SearchingApplication;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;

public class ExceptionFilterUsingSET {  //SET is a modified version of java.util.treeSet.

    public static void whitelist(String filename){  // AllowFilter.java of booksite
        SET<String> set=new SET<>();
        In in=new In(filename);
        while (!in.isEmpty()){
            set.add(in.readString());
        }
        while (!StdIn.isEmpty()){
            String s=StdIn.readString();
            if(set.contains(s)){
                System.out.println(s);
            }
        }
    }

    public static void blacklist(String filename){  // BlockFilter.java at booksite
        SET<String> set=new SET<>();
        In in=new In(filename);
        while (!in.isEmpty()){
            set.add(in.readString());
        }
        while (!StdIn.isEmpty()){
            String s=StdIn.readString();
            if(!set.contains(s)){
                System.out.println(s);
            }
        }
    }

}
