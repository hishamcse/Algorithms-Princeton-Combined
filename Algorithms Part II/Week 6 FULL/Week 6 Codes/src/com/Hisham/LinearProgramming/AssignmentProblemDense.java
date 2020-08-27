//package com.Hisham.LinearProgramming;
//
//import edu.princeton.cs.algs4.*;
//
//public class AssignmentProblemDense {
//
////     implements the successive shortest paths algorithm to solve nxn assignment problem;
////     the order of growth of the running time in the worst case is N^3.
////     very small improvement of AssignmentProblem.java
//
//    private static final double FLOATING_POINT_EPSILON = 1E-14;
//    private static final int UNMATCHED = -1;
//
//    private final int n;              // number of rows and columns
//    private final double[][] weight;  // the n-by-n cost matrix
//    private final double[] px;        // px[i] = dual variable for row i
//    private final double[] py;        // py[j] = dual variable for col j
//    private final int[] xy;           // xy[i] = j means i-j is a match
//    private final int[] yx;           // yx[j] = i means i-j is a match
//
//    public AssignmentProblemDense(double[][] weight) {
//        n = weight.length;
//        this.weight = weight.clone();
//
//        // dual variables
//        px = new double[n];
//        py = new double[n];
//
//        // initial matching is empty
//        xy = new int[n];
//        yx = new int[n];
//        for (int i = 0; i < n; i++)
//            xy[i] = UNMATCHED;
//        for (int j = 0; j < n; j++)
//            yx[j] = UNMATCHED;
//
//        // add n edges to matching
//        for (int k = 0; k < n; k++) {
//            augment();
//        }
//    }
//
//    // find shortest augmenting path and upate
//    private void augment() {
//
//        // build residual graph
//        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(2 * n + 2);
//        int s = 2 * n, t = 2 * n + 1;
//        for (int i = 0; i < n; i++) {
//            if (xy[i] == UNMATCHED)
//                G.addEdge(new DirectedEdge(s, i, 0.0));
//        }
//        for (int j = 0; j < n; j++) {
//            if (yx[j] == UNMATCHED)
//                G.addEdge(new DirectedEdge(n + j, t, py[j]));
//        }
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (xy[i] == j) G.addEdge(new DirectedEdge(n + j, i, 0.0));
//                else G.addEdge(new DirectedEdge(i, n + j, reducedCost(i, j)));
//            }
//        }
//
//        // compute shortest path from s to every other vertex
//        DenseDijkstraSP spt = new DenseDijkstraSP(G, s);   // should create a class enabling adjmatrixedgeweighteddigraph
//
//        // augment along alternating path
//        for (DirectedEdge e : spt.pathTo(t)) {
//            int i = e.from(), j = e.to() - n;
//            if (i < n) {
//                xy[i] = j;
//                yx[j] = i;
//            }
//        }
//
//        // update dual variables
//        for (int i = 0; i < n; i++)
//            px[i] += spt.distTo(i);
//        for (int j = 0; j < n; j++)
//            py[j] += spt.distTo(n + j);
//    }
//
//    // reduced cost of i-j
//    // (subtracting off minWeight reweights all weights to be non-negative)
//    private double reducedCost(int i, int j) {
//        double reducedCost = weight[i][j] + px[i] - py[j];
//
//        // to avoid issues with floating-point precision
//        double magnitude = Math.abs(weight[i][j]) + Math.abs(px[i]) + Math.abs(py[j]);
//        if (Math.abs(reducedCost) <= FLOATING_POINT_EPSILON * magnitude) return 0.0;
//
//        assert reducedCost >= 0.0;
//        return reducedCost;
//    }
//
//    // dual variable for row i
//    public double dualRow(int i) {
//        return px[i];
//    }
//
//    public double dualCol(int j) {
//        return py[j];
//    }
//
//    public int sol(int i) {
//        return xy[i];
//    }
//
//    public double weight() {
//        double total = 0.0;
//        for (int i = 0; i < n; i++) {
//            if (xy[i] != UNMATCHED)
//                total += weight[i][xy[i]];
//        }
//        return total;
//    }
//
//    public static void main(String[] args) {
//
//        int n = Integer.parseInt(args[0]);
//        double[][] weight = new double[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                weight[i][j] = StdRandom.uniform();
//
//        AssignmentProblemDense assignment = new AssignmentProblemDense(weight);
//        StdOut.println("weight = " + assignment.weight());
//        for (int i = 0; i < n; i++)
//            StdOut.println(i + "-" + assignment.sol(i));
//    }
//
//}
