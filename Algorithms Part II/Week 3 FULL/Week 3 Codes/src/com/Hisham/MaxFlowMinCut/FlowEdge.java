package com.Hisham.MaxFlowMinCut;

import edu.princeton.cs.algs4.StdOut;

public class FlowEdge {

    private final int v;
    private final int w;
    private double flow;
    private final double capacity;

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
    }

    public FlowEdge(int v, int w, double capacity, double flow) {
        this.v         = v;
        this.w         = w;
        this.capacity  = capacity;
        this.flow      = flow;
    }

    public FlowEdge(FlowEdge e) {
        this.v         = e.v;
        this.w         = e.w;
        this.capacity  = e.capacity;
        this.flow      = e.flow;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int other(int vertex) {
        if (vertex == v) {
            return w;
        }
        if (vertex == w) {
            return v;
        }
        throw new IllegalArgumentException("illegal argument");
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v) {
            return flow;
        }
        if (vertex == w) {
            return capacity - flow;
        }
        throw new IllegalArgumentException("argument illegal");
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == v) {
            flow -= delta;
        }
        if (vertex == w) {
            flow += delta;
        }
        throw new IllegalArgumentException("argument is illegal");
    }

    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }

    public static void main(String[] args) {
        FlowEdge e = new FlowEdge(12, 23, 4.56);
        StdOut.println(e);
    }
}
