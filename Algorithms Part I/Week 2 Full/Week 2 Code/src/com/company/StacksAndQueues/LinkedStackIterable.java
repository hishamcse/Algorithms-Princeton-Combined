package com.company.StacksAndQueues;

import java.util.Iterator;

public class LinkedStackIterable<String> implements Iterable<String>{

    public Iterator<String> iterator(){return new ListIterator();}

    private Node<String> first=null;

    public static class Node<String>{
        String item;
        Node<String> next;
    }

    public boolean isempty(){
        return first==null;
    }

    public void push(String item){
        Node<String> oldfirst=first;
        first= new Node<>();
        first.item=item;
        first.next=oldfirst;
    }

    public String pop(){
        String item=first.item;
        first=first.next;
        return item;
    }

    public class ListIterator implements Iterator<String>{

        private Node<String> current=first;

        public boolean hasNext(){
            return current!=null;
        }

        public String next(){
            String item=current.item;
            current=current.next;
            return item;
        }
    }
}
