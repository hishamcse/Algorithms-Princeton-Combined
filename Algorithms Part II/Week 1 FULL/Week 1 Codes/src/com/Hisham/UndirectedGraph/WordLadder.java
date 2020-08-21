package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;

public class WordLadder {        // see booksite for problem description

    public static boolean neighbours(String str1,String str2){
        int diff=0;
        for(int i=0;i<str1.length();i++){
            if(str1.charAt(i)!=str2.charAt(i)){
                diff++;
                if(diff>1){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        In in=new In(StdIn.readLine());
        IndexSET<String> words=new IndexSET<>();

        while (!in.isEmpty()){
            String word=in.readLine();
            words.add(word);
        }

        Graph g=new Graph(words.size());
        for(String str1:words.keys()){
            for(String str2:words.keys()){
                if(str1.compareTo(str2)<0 && neighbours(str1,str2)){
                    g.addEdge(words.indexOf(str1), words.indexOf(str2));
                }
            }
        }

        String from=StdIn.readString();
        String to=StdIn.readString();
        if(!words.contains(from) || !words.contains(to)){
            throw new RuntimeException("word doesnt exist");
        }
        BreadthFirstPaths bfs=new BreadthFirstPaths(g, words.indexOf(from));
        if(bfs.hasPathTo(words.indexOf(to))){
            System.out.println("length of shortest ladder = "+(bfs.distTo(words.indexOf(to))));
            for(int v:bfs.pathTo(words.indexOf(to))){
                System.out.println(words.keyOf(v));
            }
        }else{
            System.out.println("not connected");
        }
    }
}
