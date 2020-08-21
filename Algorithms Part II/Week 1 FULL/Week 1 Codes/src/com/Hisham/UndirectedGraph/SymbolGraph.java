package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Graph;

public class SymbolGraph {          // see booksite SymbolGraph.java

    private final ST<String,Integer> st;
    private final String[] keys;
    private final Graph graph;

    public SymbolGraph(String filename,String delimiter){
        st=new ST<>();
        In in=new In(filename);
        while (!in.isEmpty()){
            String[] a=in.readLine().split(delimiter);
            for (String s : a) {
                if (!st.contains(s)) {
                    st.put(s, st.size());
                }
            }
        }

        keys=new String[st.size()];
        for(String key:st.keys()){
            keys[st.get(key)]=key;
        }

        graph=new Graph(st.size());
        in=new In(filename);
        while (in.hasNextLine()){
            String[] a=in.readLine().split(delimiter);
            int v=st.get(a[0]);
            for(int i=1;i<a.length;i++){
                int w=st.get(a[i]);
                graph.addEdge(v,w);
            }
        }
    }

    public boolean contains(String key){
        return st.contains(key);
    }

    public int indexOf(String key){
        return st.get(key);
    }

    public String nameOf(int v){
        return keys[v];
    }

    public Graph graph(){
        return graph;
    }

    public static void main(String[] args) {
        String filename  = StdIn.readLine();
        String delimiter = StdIn.readLine();
        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        Graph graph = sg.graph();
        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            if (sg.contains(source)) {
                int s = sg.indexOf(source);
                for (int v : graph.adj(s)) {
                    StdOut.println("   " + sg.nameOf(v));
                }
            }
            else {
                StdOut.println("input not contain '" + source + "'");
            }
        }
    }

}
