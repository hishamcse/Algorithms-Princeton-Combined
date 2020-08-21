package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BareBonesWebCrawler {      // application of bfs

    public static void main(String[] args) {

        Queue<String> queue=new Queue<>();
        SET<String> set=new SET<>();

        String root="https://www.cs.princeton.edu";
        queue.enqueue(root);
        set.add(root);

        while (!queue.isEmpty()){
            String v= queue.dequeue();
            System.out.println(v);
            In in=new In(v);
            String input=in.readAll();

            String regex="(http|https)://(\\w+\\.)+(edu|com|gov|org)";
            Pattern pattern=Pattern.compile(regex);
            Matcher matcher= pattern.matcher(input);

            while (matcher.find()){
                String s=matcher.group();
                if(!set.contains(s)){
                    set.add(s);
                    queue.enqueue(s);
                    System.out.println(s);
                }
            }
        }

    }

}
