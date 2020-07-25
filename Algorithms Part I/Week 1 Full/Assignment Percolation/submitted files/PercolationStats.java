import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] estimation;
    private final double d = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int k = 0;
        estimation = new double[trials];
        for (int r = 0; r < trials; r++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(n) + 1;
                int j = StdRandom.uniform(n) + 1;
                percolation.open(i, j);

                if (percolation.percolates()) {
                    estimation[k] = (double) percolation.numberOfOpenSites() / (double) (n * n);
                    k++;
                    break;
                }
            }
        }
    }

    public double mean() {
        return StdStats.mean(estimation);
    }

    public double stddev() {
        return StdStats.stddev(estimation);
    }

    public double confidenceLo() {
        return mean() - ((d * stddev()) / Math.sqrt(estimation.length));
    }

    public double confidenceHi() {
        return mean() + ((d * stddev()) / Math.sqrt(estimation.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println("mean                   = " + percolationStats.mean());
        StdOut.println("stddev                 = " + percolationStats.stddev());
        StdOut.println("95% confidence interval= [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
    }

}