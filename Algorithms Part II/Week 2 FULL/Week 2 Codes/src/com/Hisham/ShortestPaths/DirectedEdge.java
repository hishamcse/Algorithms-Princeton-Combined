package com.Hisham.ShortestPaths;

public class DirectedEdge implements Comparable<DirectedEdge> {

    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int compareTo(DirectedEdge that) {
        return Double.compare(this.weight, that.weight);
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return String.format("%d->%d %.5f", v, w, weight);
    }

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(12, 34, 5.67);
        System.out.println(e);
        System.out.println(e.compareTo(new DirectedEdge(12, 2323, 2.34)));
        System.out.println(e.from());
        System.out.println(e.to());
        System.out.println(e.weight());
    }
}
