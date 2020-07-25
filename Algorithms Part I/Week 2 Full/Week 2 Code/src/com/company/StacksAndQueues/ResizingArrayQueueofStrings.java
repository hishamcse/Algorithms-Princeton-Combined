package com.company.StacksAndQueues;

public class ResizingArrayQueueofStrings {

    String []q;
    int n;
    int first,last;

    public ResizingArrayQueueofStrings(){
        q=new String[2];
        n=0;first=0;last=0;
    }

    public boolean isEmpty(){
        return n==0;
    }

    private void resize(int capacity){
        String[] t=new String[capacity];
        for(int i=0;i<n;i++){
            t[i]=q[(first+i)%q.length];
        }
        q=t;
        first=0;
        last=n;
    }

    public void Enqueue(String item){
        if(n==q.length){resize(2*q.length);}
        q[last++]=item;
        if(last==q.length) {last=0;}
        n++;
    }

    public String dequeue(){
        String item=q[first];
        q[first]=null;
        n--;
        first++;
        if(first==q.length){first=0;}
        if(n>0 && n==q.length/4){
            resize(q.length/2);
        }
        return item;
    }
}
