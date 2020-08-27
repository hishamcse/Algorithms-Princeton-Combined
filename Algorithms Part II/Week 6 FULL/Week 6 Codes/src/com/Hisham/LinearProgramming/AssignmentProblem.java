package com.Hisham.LinearProgramming;

import edu.princeton.cs.algs4.*;

public class AssignmentProblem {

//     implements the successive shortest paths algorithm to solve n-by-n assignment problem ;
//     the order of growth of the running time in the worst case is N^3 log N

    private static final double FLOATING_POINT_EPSILON = 1E-14;
    private static final int UNMATCHED = -1;

    private final int n;              // number of rows and columns
    private final double[][] weight;  // the n-by-n cost matrix
    private double minWeight;         // minimum value of any weight
    private final double[] px;        // px[i] = dual variable for row i
    private final double[] py;        // py[j] = dual variable for col j
    private final int[] xy;           // xy[i] = j means i-j is a match
    private final int[] yx;           // yx[j] = i means i-j is a match

    public AssignmentProblem(double[][] weight) {
        n = weight.length;
        this.weight = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Double.isNaN(weight[i][j]))
                    throw new IllegalArgumentException("weight " + i + "-" + j + " is NaN");
                if (weight[i][j] < minWeight) minWeight = weight[i][j];
                this.weight[i][j] = weight[i][j];
            }
        }

        // dual variables
        px = new double[n];
        py = new double[n];

        // initial matching is empty
        xy = new int[n];
        yx = new int[n];
        for (int i = 0; i < n; i++)
            xy[i] = UNMATCHED;
        for (int j = 0; j < n; j++)
            yx[j] = UNMATCHED;

        // add n edges to matching
        for (int k = 0; k < n; k++) {
            augment();
        }
    }

    // find shortest augmenting path and upate
    private void augment() {

        // build residual graph
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * n + 2);
        int s = 2 * n, t = 2 * n + 1;
        for (int i = 0; i < n; i++) {
            if (xy[i] == UNMATCHED)
                G.addEdge(new DirectedEdge(s, i, 0.0));
        }
        for (int j = 0; j < n; j++) {
            if (yx[j] == UNMATCHED)
                G.addEdge(new DirectedEdge(n + j, t, py[j]));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (xy[i] == j) G.addEdge(new DirectedEdge(n + j, i, 0.0));
                else G.addEdge(new DirectedEdge(i, n + j, reducedCost(i, j)));
            }
        }

        // compute shortest path from s to every other vertex
        DijkstraSP spt = new DijkstraSP(G, s);

        // augment along alternating path
        for (DirectedEdge e : spt.pathTo(t)) {
            int i = e.from(), j = e.to() - n;
            if (i < n) {
                xy[i] = j;
                yx[j] = i;
            }
        }

        // update dual variables
        for (int i = 0; i < n; i++)
            px[i] += spt.distTo(i);
        for (int j = 0; j < n; j++)
            py[j] += spt.distTo(n + j);
    }

    // reduced cost of i-j
    // (subtracting off minWeight reweights all weights to be non-negative)
    private double reducedCost(int i, int j) {
        double reducedCost = (weight[i][j] - minWeight) + px[i] - py[j];

        // to avoid issues with floating-point precision
        double magnitude = Math.abs(weight[i][j]) + Math.abs(px[i]) + Math.abs(py[j]);
        if (Math.abs(reducedCost) <= FLOATING_POINT_EPSILON * magnitude) return 0.0;

        assert reducedCost >= 0.0;
        return reducedCost;
    }

    // dual variable for row i
    public double dualRow(int i) {
        return px[i];
    }

    public double dualCol(int j) {
        return py[j];
    }

    public int sol(int i) {
        return xy[i];
    }

    public double weight() {
        double total = 0.0;
        for (int i = 0; i < n; i++) {
            if (xy[i] != UNMATCHED)
                total += weight[i][xy[i]];
        }
        return total;
    }

    public static void main(String[] args) {
        // create random n-by-n matrix
        int n = Integer.parseInt(StdIn.readLine());
        double[][] weight = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weight[i][j] = StdRandom.uniform(900) + 100;  // 3 digits
            }
        }

        // solve assignment problem
        AssignmentProblem assignment = new AssignmentProblem(weight);
        StdOut.printf("weight = %.0f\n", assignment.weight());
        StdOut.println();

        // print n-by-n matrix and optimal solution
        if (n >= 20) return;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == assignment.sol(i))
                    StdOut.printf("*%.0f ", weight[i][j]);
                else
                    StdOut.printf(" %.0f ", weight[i][j]);
            }
            StdOut.println();
        }
    }

}
