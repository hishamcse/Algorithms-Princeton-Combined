package com.company.StacksAndQueues;

import java.util.NoSuchElementException;

public class ResizingArrayStackOfStrings {

    String []s;
    int n=0;

    public ResizingArrayStackOfStrings(){
        s=new String[1];
    }

    public boolean isEmpty(){
        return n==0;
    }

    private void resize(int capacity){
        assert capacity>=n;

        String []t=new String[capacity];
        for(int i=0;i<n;i++){
            t[i]=s[i];
        }
        s=t;
    }

    public void push(String item){
        if(n==s.length){resize(2*s.length);}
        s[n++]=item;
    }

    public String pop(){
        if(isEmpty()){throw new NoSuchElementException("Stack empty"); }

        String item=s[--n];
        s[n]=null;
        if(n>0 && n==s.length/4){resize(s.length/2);}
        return item;
    }

}
