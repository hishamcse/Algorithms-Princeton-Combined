package com.company.StacksAndQueues;

public class FixedCapacityStackOfStrings {

       String []s;
       int n=0;

       public FixedCapacityStackOfStrings(int capacity){
           s=new String[capacity];
       }

       public boolean isEmpty(){
           return n==0;
       }

       public void push(String item){
           s[n++]=item;
       }

       public String pop(){
           String p= s[--n];
           s[n]=null;
           return p;
       }
}
