package com.company.StacksAndQueues;

public class LinkedQueueofStrings {

    private Node first=null;
    private Node last=null;

    public static class Node{
        Node next;
        private String item;
    }

    public boolean isEmpty(){
        return first==null;
    }

    public void Enqueue(String item){
        Node oldlast=last;
        last=new Node();
        last.item=item;
        last.next=null;
        if(isEmpty()){first=last;}
        else {oldlast.next=last;}
    }

    public String dequeue(){
        String item=first.item;
        first=first.next;
        if(isEmpty()){last=null;}
        return item;
    }
}
