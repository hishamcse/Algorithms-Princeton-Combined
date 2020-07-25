package com.company.StacksAndQueues;

public class LinkedStackofStrings {

    private Node first=null;

    public static class Node{
        String item;
        Node next;
    }

    public boolean isempty(){
        return first==null;
    }

    public void push(String item){
        Node oldfirst=first;
        first=new Node();
        first.item=item;
        first.next=oldfirst;
    }

    public String pop(){
        String item=first.item;
        first=first.next;
        return item;
    }
}
