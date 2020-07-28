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

