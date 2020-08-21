package com.Hisham.MaxFlowMinCut;

import edu.princeton.cs.algs4.*;

import java.util.LinkedList;
import java.util.List;

public class MaxWeightClosure {

    //idea : https://github.com/yixuaz/algorithm4-princeton-cos226/blob/master/princetonSolution/src/part2/week3/maxflow/MaxWeightClosure.java
    //wiki : https://en.wikipedia.org/wiki/Closure_problem

    private final List<Vertex> list;
    private final double weight;

    private static class Vertex {
        private final int id;
        private final double weight;

        public Vertex(int id, double weight) {
            this.id = id;
            this.weight = weight;
        }

        public String toString() {
            return id + " " + weight;
        }
    }

    public MaxWeightClosure(Digraph g, double[] weights) {
        assert g.V() == weights.length;

        List<Vertex> vertices = new LinkedList<>();
        for (int v = 0; v < g.V(); v++) {
            vertices.add(new Vertex(v, weights[v]));
        }

        int V = g.V();
        int s = V;          // source
        int t = V + 1;      // target

        // flowNetwork creation
        FlowNetwork flowNetwork = new FlowNetwork(V + 2);
        double total = 0.0;
        for (Vertex v : vertices) {
            if (v.weight > 0) {
                total += v.weight;
                flowNetwork.addEdge(new FlowEdge(s, v.id, v.weight));
            } else {
                flowNetwork.addEdge(new FlowEdge(v.id, t, -v.weight));
            }
        }

        for (Vertex v : vertices) {
            for (int w : g.adj(v.id)) {
                flowNetwork.addEdge(new FlowEdge(v.id, w, Double.POSITIVE_INFINITY));
            }
        }

        // maxFlow algorithm
        FordFulkerson maxFlow = new FordFulkerson(flowNetwork, s, t);
        list = new LinkedList<>();
        for (Vertex v : vertices) {
            if (maxFlow.inCut(v.id)) {
                list.add(v);
            }
        }
        weight = total - maxFlow.value();
    }

    public double weight() {
        return weight;
    }

    public Iterable<Vertex> set() {
        return list;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        Digraph g = new Digraph(in);
        double[] weights = new double[g.V()];
        for (int i = 0; i < g.V(); i++) {
            weights[i] = StdIn.readDouble();
        }
        MaxWeightClosure maxWeightClosure = new MaxWeightClosure(g, weights);
        System.out.println(maxWeightClosure.weight());
        for (Vertex v : maxWeightClosure.set()) {
            System.out.println(v);
        }
    }

    // test 1(weight=49)
    // digraph for 4 vertices
    // weights : 8,9,20,12

    // test 2(weight=0)
    // digraph for 4 vertices
    // weights : -8,-9,-20,12

    // test 3(weight=17)
    // digraph for 6 vertices
    // weights : 8,-9,-20,12,-10,5

}
