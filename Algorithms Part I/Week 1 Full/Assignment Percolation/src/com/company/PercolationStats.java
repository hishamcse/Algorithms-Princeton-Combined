package com.company;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] estimation;
    private final double d=1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int k = 0;
        estimation = new double[trials];
        for (int r=0;r<trials;r++){
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(n)+1;
                int j = StdRandom.uniform(n)+1;
                percolation.open(i, j);

                if (percolation.percolates()) {
                    estimation[k] = (double) percolation.numberOfOpenSites() / (double) (n * n);
                    k++;
                    break;
                }
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(estimation);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(estimation);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((d * stddev()) / Math.sqrt(estimation.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((d * stddev()) / Math.sqrt(estimation.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
//        Stopwatch sw = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println("mean                   = " + percolationStats.mean());
        StdOut.println("stddev                 = " + percolationStats.stddev());
        StdOut.println("95% confidence interval= [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
//        System.out.printf("Total time: %f secs. (for N=%d, T=%d)",
//                sw.elapsedTime(), n, t);
    }

}

//
//import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.StdOut;
//
//public class PercolationStats {
//    private static final double CONFIDENCE_95 = 1.96;
//    private final double[] thresholds;
//    private double mean = -1;
//    private double stddev = -1;
//
//    // perform trials independent experiments on an n-by-n grid
//    public PercolationStats(int n, int trials) {
//        if (n < 1 || trials < 1) {
//            throw new IllegalArgumentException();
//        }
//
//        thresholds = new double[trials];
//
//        for (int i = 0; i < trials; i++) {
//            Percolation percolation = new Percolation(n);
//
//            while (!percolation.percolates()) {
//                int row = StdRandom.uniform(n) + 1;
//                int col = StdRandom.uniform(n) + 1;
//
//                percolation.open(row, col);
//            }
//
//            thresholds[i] = percolation.numberOfOpenSites() / (double) (n * n);
//        }
//    }
//
//    // sample mean of percolation threshold
//    public double mean() {
//        if (mean == -1) {
//            mean = StdStats.mean(thresholds);
//        }
//
//        return mean;
//    }
//
//    // sample standard deviation of percolation threshold
//    public double stddev() {
//        if (stddev == -1) {
//            stddev = StdStats.stddev(thresholds);
//        }
//
//        return stddev;
//    }
//
//    // low  endpoint of 95% confidence interval
//    public double confidenceLo() {
//        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(thresholds.length);
//    }
//
//    // high endpoint of 95% confidence interval
//    public double confidenceHi() {
//        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(thresholds.length);
//    }
//
//    // test client (described below)
//    public static void main(String[] args) {
//        int n = StdIn.readInt();
//        int t = StdIn.readInt();
////        Stopwatch sw = new Stopwatch();
//        PercolationStats percolationStats = new PercolationStats(n, t);
//        StdOut.println("mean                   = " + percolationStats.mean());
//        StdOut.println("stddev                 = " + percolationStats.stddev());
//        StdOut.println("95% confidence interval= [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
////        System.out.printf("Total time: %f secs. (for N=%d, T=%d)",
////                sw.elapsedTime(), n, t);
//    }
//
//}
