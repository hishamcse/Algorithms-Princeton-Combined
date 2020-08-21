package com.Hisham.MST;

public class Edge implements Comparable<Edge>{

    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v,int w,double weight){
        this.v=v;
        this.w=w;
        this.weight=weight;
    }

    public int either(){
        return v;
    }

    public int other(int vertex){
        if(vertex==v){
            return w;
        }
        if(vertex==w){
            return v;
        }
        throw new IllegalArgumentException("argument is invalid");
    }

    public int compareTo(Edge that){
        return Double.compare(this.weight,that.weight);
    }

    public double weight(){
        return weight;
    }

    public String toString(){
        return String.format("%d-%d %.5f", v, w, weight);
    }

    public static void main(String[] args) {
        Edge e = new Edge(12, 34, 5.67);
        System.out.println(e);
        System.out.println(e.compareTo(new Edge(12,2323,2.34)));
        System.out.println(e.either());
        System.out.println(e.other(12));
        System.out.println(e.weight());
    }

}
