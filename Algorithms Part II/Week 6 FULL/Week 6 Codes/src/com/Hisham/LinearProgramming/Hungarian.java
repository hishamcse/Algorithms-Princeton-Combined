package com.Hisham.LinearProgramming;

import edu.princeton.cs.algs4.*;

public class Hungarian {

//    Hungarian algorithm; the order of growth of the running time in the worst case is N^4
//    Solve an n-by-n assignment problem. Bare-bones implementation:
//    - takes n^5 time in worst case.
//    - assumes weights are >= 0  (add a large constant if not)
//    Use BipartiteMatching.java or HopcroftKarp.java to compute
//    max cardinality matching (instead of reducing to maxflow). to reduce it to N^4

    private static final double FLOATING_POINT_EPSILON = 1E-14;

    private final int n;              // number of rows and columns
    private final double[][] weight;  // the n-by-n weight matrix
    private final double[] x;         // dual variables for rows
    private final double[] y;         // dual variables for columns
    private final int[] xy;           // xy[i] = j means i-j is a match
    private int[] yx;                 // yx[j] = i means i-j is a match

    public Hungarian(double[][] weight) {
        n = weight.length;
        this.weight = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!(weight[i][j] >= 0.0))
                    throw new IllegalArgumentException("weights must be non-negative");
                this.weight[i][j] = weight[i][j];
            }
        }

        x = new double[n];
        y = new double[n];
        xy = new int[n];
        yx = new int[n];
        for (int i = 0; i < n; i++)
            xy[i] = -1;
        for (int j = 0; j < n; j++)
            yx[j] = -1;

        while (true) {

            // build graph of 0-reduced cost edges
            FlowNetwork G = new FlowNetwork(2 * n + 2);
            int s = 2 * n, t = 2 * n + 1;
            for (int i = 0; i < n; i++) {
                if (xy[i] == -1) G.addEdge(new FlowEdge(s, i, 1.0));
                else G.addEdge(new FlowEdge(s, i, 1.0, 1.0));
            }
            for (int j = 0; j < n; j++) {
                if (yx[j] == -1) G.addEdge(new FlowEdge(n + j, t, 1.0));
                else G.addEdge(new FlowEdge(n + j, t, 1.0, 1.0));
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (reducedCost(i, j) == 0) {
                        if (xy[i] != j) G.addEdge(new FlowEdge(i, n + j, 1.0));
                        else G.addEdge(new FlowEdge(i, n + j, 1.0, 1.0));
                    }
                }
            }

            // to make n^4, start from previous solution
            // (i.e., initialize flow to correspond to current matching;
            //  as a result, there will be exactly n augmenting paths
            //  over all calls to FordFulkerson because each one increases
            //  the value of the flow by 1)
            FordFulkerson ff = new FordFulkerson(G, s, t);

            // current matching
            for (int i = 0; i < n; i++)
                xy[i] = -1;
            for (int j = 0; j < n; j++)
                yx[j] = -1;
            for (int i = 0; i < n; i++) {
                for (FlowEdge e : G.adj(i)) {
                    if ((e.from() == i) && (e.flow() > 0)) {
                        xy[i] = e.to() - n;    // as e.to() is n+j.so,n+j-n=j
                        yx[e.to() - n] = i;    // just the opposite
                    }
                }
            }

            // perfect matching
            if (ff.value() == n) break;

            // find bottleneck weight
            double max = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (ff.inCut(i) && !ff.inCut(n + j) && (reducedCost(i, j) < max))  // if exists in only one cut
                        max = reducedCost(i, j);

            // update dual variables
            for (int i = 0; i < n; i++)
                if (!ff.inCut(i)) x[i] -= max;
            for (int j = 0; j < n; j++)
                if (!ff.inCut(n + j)) y[j] += max;

            StdOut.println("value = " + ff.value());
        }
    }

    // reduced cost of i-j
    // (subtracting off minWeight reweights all weights to be non-negative)
    private double reducedCost(int i, int j) {
        double reducedCost = weight[i][j] - x[i] - y[j];

        // to avoid issues with floating-point precision
        double magnitude = Math.abs(weight[i][j]) + Math.abs(x[i]) + Math.abs(y[j]);
        if (Math.abs(reducedCost) <= FLOATING_POINT_EPSILON * magnitude) return 0.0;

        assert reducedCost >= 0.0;
        return reducedCost;
    }

    private double weight() {
        double totalWeight = 0.0;
        for (int i = 0; i < n; i++)
            totalWeight += weight[i][xy[i]];
        return totalWeight;
    }

    private int sol(int i) {
        return xy[i];
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(StdIn.readLine());
        double[][] weight = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                weight[i][j] = StdRandom.uniform(0.0, 1.0);

        Hungarian assignment = new Hungarian(weight);
        StdOut.println("weight = " + assignment.weight());
        for (int i = 0; i < n; i++)
            StdOut.println(i + "-" + assignment.sol(i));
    }
}
