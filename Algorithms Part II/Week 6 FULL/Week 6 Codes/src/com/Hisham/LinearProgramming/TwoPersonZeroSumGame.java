package com.Hisham.LinearProgramming;

import edu.princeton.cs.algs4.LinearProgramming;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class TwoPersonZeroSumGame {

    private final int m;            // number of rows
    private final int n;            // number of columns
    private final LinearProgramming lp;   // linear program solver
    private double constant;        // constant added to each entry in payoff matrix
    // (0 if all entries are strictly positive)

    public TwoPersonZeroSumGame(double[][] payoff) {
        m = payoff.length;
        n = payoff[0].length;

        double[] c = new double[n];
        double[] b = new double[m];
        double[][] A = new double[m][n];
        for (int i = 0; i < m; i++)
            b[i] = 1.0;
        for (int j = 0; j < n; j++)
            c[j] = 1.0;

        // find smallest entry
        constant = Double.POSITIVE_INFINITY;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (payoff[i][j] < constant)
                    constant = payoff[i][j];

        // add constant  to every entry to make strictly positive
        if (constant <= 0) constant = -constant + 1;
        else constant = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = payoff[i][j] + constant;

        lp = new LinearProgramming(A, b, c);
    }

    public double value() {
        return 1.0 / scale() - constant;
    }

    // sum of x[j]
    private double scale() {
        double[] x = lp.primal();
        double sum = 0.0;
        for (int j = 0; j < n; j++)
            sum += x[j];
        return sum;
    }

    public double[] row() {
        double scale = scale();
        double[] x = lp.primal();
        for (int j = 0; j < n; j++)
            x[j] /= scale;
        return x;
    }

    public double[] column() {
        double scale = scale();
        double[] y = lp.dual();
        for (int i = 0; i < m; i++)
            y[i] /= scale;
        return y;
    }

    private static void test(String description, double[][] payoff) {
        StdOut.println();
        StdOut.println(description);
        StdOut.println("------------------------------------");
        int m = payoff.length;
        int n = payoff[0].length;
        TwoPersonZeroSumGame zeroSum = new TwoPersonZeroSumGame(payoff);
        double[] x = zeroSum.row();
        double[] y = zeroSum.column();

        StdOut.print("x[] = [");
        for (int j = 0; j < n - 1; j++)
            StdOut.printf("%8.4f, ", x[j]);
        StdOut.printf("%8.4f]\n", x[n - 1]);

        StdOut.print("y[] = [");
        for (int i = 0; i < m - 1; i++)
            StdOut.printf("%8.4f, ", y[i]);
        StdOut.printf("%8.4f]\n", y[m - 1]);
        StdOut.println("value =  " + zeroSum.value());

    }

    // row = { 4/7, 3/7 }, column = { 0, 4/7, 3/7 }, value = 20/7
    // http://en.wikipedia.org/wiki/Zero-sum
    private static void test1() {
        double[][] payoff = {
                {30, -10, 20},
                {10, 20, -20}
        };
        test("wikipedia", payoff);
    }

    // skew-symmetric => value = 0
    // Linear Programming by Chvatal, p. 230
    private static void test2() {
        double[][] payoff = {
                {0, 2, -3, 0},
                {-2, 0, 0, 3},
                {3, 0, 0, -4},
                {0, -3, 4, 0}
        };
        test("Chvatal, p. 230", payoff);
    }

    // Linear Programming by Chvatal, p. 234
    // row    = { 0, 56/99, 40/99, 0, 0, 2/99, 0, 1/99 }
    // column = { 28/99, 30/99, 21/99, 20/99 }
    // value  = 4/99
    private static void test3() {
        double[][] payoff = {
                {0, 2, -3, 0},
                {-2, 0, 0, 3},
                {3, 0, 0, -4},
                {0, -3, 4, 0},
                {0, 0, -3, 3},
                {-2, 2, 0, 0},
                {3, -3, 0, 0},
                {0, 0, 4, -4}
        };
        test("Chvatal, p. 234", payoff);
    }

    // Linear Programming by Chvatal, p. 236
    // row    = { 0, 2/5, 7/15, 0, 2/15, 0, 0, 0 }
    // column = { 2/3, 0, 0, 1/3 }
    // value  = -1/3
    private static void test4() {
        double[][] payoff = {
                {0, 2, -1, -1},
                {0, 1, -2, -1},
                {-1, -1, 1, 1},
                {-1, 0, 0, 1},
                {1, -2, 0, -3},
                {1, -1, -1, -3},
                {0, -3, 2, -1},
                {0, -2, 1, -1},
        };
        test("Chvatal p. 236", payoff);
    }

    // rock, paper, scissors
    // row    = { 1/3, 1/3, 1/3 }
    // column = { 1/3, 1/3, 1/3 }
    private static void test5() {
        double[][] payoff = {
                {0, -1, 1},
                {1, 0, -1},
                {-1, 1, 0}
        };
        test("rock, paper, scissors", payoff);
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();

        int m = Integer.parseInt(StdIn.readLine());
        int n = Integer.parseInt(StdIn.readLine());
        double[][] payoff = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                payoff[i][j] = StdRandom.uniform(-0.5, 0.5);
        test("random " + m + "-by-" + n, payoff);
    }
}
