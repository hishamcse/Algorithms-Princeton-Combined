package com.Hisham.LinearProgramming;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Simplex {    // implementation of LinearProgramming.java at booksite

    // Bare bones simplex algorithm for maximization.if we want minimization,
    // see the pg 41 of the princeton slide.It is very easy to convert the code for minimization

    private static final double EPSILON = 1.0E-10;

    private final double[][] a;
    private final int m, n;
    private final int[] basis;

    public Simplex(double[][] A, double[] b, double[] c) {
        m = b.length;
        n = c.length;
        a = new double[m + 1][m + n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = A[i][j];
            }
        }

        for (int j = n; j < m + n; j++) {
            a[j - n][j] = 1.0;
        }
        for (int j = 0; j < n; j++) {
            a[m][j] = c[j];
        }
        for (int i = 0; i < m; i++) {
            a[i][m + n] = b[i];
        }

        basis = new int[m];
        for (int i = 0; i < m; i++) {
            basis[i] = n + i;
        }

        solve();
    }

    private int bland() {
        for (int q = 0; q < m + n; q++) {
            if (a[m][q] > 0) {
                return q;
            }
        }
        return -1;
    }

    private int minRatioRule(int q) {
        int z = -1;
        for (int p = 0; p < m; p++) {
            if (a[p][q] <= 0.0) {
                continue;
            }
            if (z == -1) {
                z = p;
            }
            if ((a[p][m + n] / a[p][q]) < a[z][m + n] / a[z][q]) {
                z = p;
            }
        }
        return z;
    }

    // Gauss-Jordan elimination
    public void pivot(int p, int q) {
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m + n; j++) {
                if (i != p && j != q) {
                    a[i][j] -= a[p][j] * a[i][q] / a[p][q];
                }
            }
        }
        for (int i = 0; i <= m; i++) {
            if (i != p) {
                a[i][q] = 0.0;
            }
        }
        for (int j = 0; j <= m + n; j++) {
            if (j != q) {
                a[p][j] /= a[p][q];
            }
        }
        a[p][q] = 1.0;
    }

    public void solve() {
        while (true) {
            int q = bland();
            if (q == -1) {
                break;
            }
            int p = minRatioRule(q);
            if (p == -1) throw new ArithmeticException("Linear program is unbounded");

            pivot(p, q);
            basis[p] = q;
        }
    }

    // index of a non-basic column with most positive cost
    private int dantZig() {
        int q = 0;
        for (int j = 1; j <= m + n; j++) {
            if (a[m][j] > a[m][q]) {
                q = j;
            }
        }
        if (a[m][q] <= 0.0) {
            return -1;
        }
        return q;
    }

    public double value() {
        return -a[m][m + n];
    }

    // Returns the optimal primal solution to this linear program.
    public double[] primal() {
        double[] x = new double[n];
        for (int i = 0; i < m; i++)
            if (basis[i] < n) x[basis[i]] = a[i][m + n];
        return x;
    }

    // Returns the optimal dual solution to this linear program
    public double[] dual() {
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            y[i] = -a[m][n + i];
        return y;
    }

    // is the solution primal feasible?
    private boolean isPrimalFeasible(double[][] A, double[] b) {
        double[] x = primal();

        // check that x >= 0
        for (int j = 0; j < x.length; j++) {
            if (x[j] < 0.0) {
                StdOut.println("x[" + j + "] = " + x[j] + " is negative");
                return false;
            }
        }

        // check that Ax <= b
        for (int i = 0; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            if (sum > b[i] + EPSILON) {
                StdOut.println("not primal feasible");
                StdOut.println("b[" + i + "] = " + b[i] + ", sum = " + sum);
                return false;
            }
        }
        return true;
    }

    // is the solution dual feasible?
    private boolean isDualFeasible(double[][] A, double[] c) {
        double[] y = dual();

        // check that y >= 0
        for (int i = 0; i < y.length; i++) {
            if (y[i] < 0.0) {
                StdOut.println("y[" + i + "] = " + y[i] + " is negative");
                return false;
            }
        }

        // check that yA >= c
        for (int j = 0; j < n; j++) {
            double sum = 0.0;
            for (int i = 0; i < m; i++) {
                sum += A[i][j] * y[i];
            }
            if (sum < c[j] - EPSILON) {
                StdOut.println("not dual feasible");
                StdOut.println("c[" + j + "] = " + c[j] + ", sum = " + sum);
                return false;
            }
        }
        return true;
    }

    // check that optimal value = cx = yb
    private boolean isOptimal(double[] b, double[] c) {
        double[] x = primal();
        double[] y = dual();
        double value = value();

        // check that value = cx = yb
        double value1 = 0.0;
        for (int j = 0; j < x.length; j++)
            value1 += c[j] * x[j];
        double value2 = 0.0;
        for (int i = 0; i < y.length; i++)
            value2 += y[i] * b[i];
        if (Math.abs(value - value1) > EPSILON || Math.abs(value - value2) > EPSILON) {
            StdOut.println("value = " + value + ", cx = " + value1 + ", yb = " + value2);
            return false;
        }
        return true;
    }

    // print tableaux
    private void show() {
        StdOut.println("m = " + m);
        StdOut.println("n = " + n);
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m + n; j++) {
                StdOut.printf("%7.2f ", a[i][j]);
                // StdOut.printf("%10.7f ", a[i][j]);
            }
            StdOut.println();
        }
        StdOut.println("value = " + value());
        for (int i = 0; i < m; i++)
            if (basis[i] < n) StdOut.println("x_" + basis[i] + " = " + a[i][m + n]);
        StdOut.println();
    }

    private static void test(double[][] A, double[] b, double[] c) {
        Simplex lp;
        try {
            lp = new Simplex(A, b, c);
        } catch (ArithmeticException e) {
            e.printStackTrace();
            return;
        }

        StdOut.println("value = " + lp.value());
        double[] x = lp.primal();
        for (int i = 0; i < x.length; i++)
            StdOut.println("x[" + i + "] = " + x[i]);
        double[] y = lp.dual();
        for (int j = 0; j < y.length; j++)
            StdOut.println("y[" + j + "] = " + y[j]);
    }

    private static void test1() {
        double[][] A = {
                {-1, 1, 0},
                {1, 4, 0},
                {2, 1, 0},
                {3, -4, 0},
                {0, 0, 1},
        };
        double[] c = {1, 1, 1};
        double[] b = {5, 45, 27, 24, 4};
        test(A, b, c);
    }

    // x0 = 12, x1 = 28, opt = 800
    private static void test2() {
        double[] c = {13.0, 23.0};
        double[] b = {480.0, 160.0, 1190.0};
        double[][] A = {
                {5.0, 15.0},
                {4.0, 4.0},
                {35.0, 20.0},
        };
        test(A, b, c);
    }

    // unbounded
    private static void test3() {
        double[] c = {2.0, 3.0, -1.0, -12.0};
        double[] b = {3.0, 2.0};
        double[][] A = {
                {-2.0, -9.0, 1.0, 9.0},
                {1.0, 1.0, -1.0, -2.0},
        };
        test(A, b, c);
    }

    // degenerate - cycles if you choose most positive objective function coefficient
    private static void test4() {
        double[] c = {10.0, -57.0, -9.0, -24.0};
        double[] b = {0.0, 0.0, 1.0};
        double[][] A = {
                {0.5, -5.5, -2.5, 9.0},
                {0.5, -1.5, -0.5, 1.0},
                {1.0, 0.0, 0.0, 0.0},
        };
        test(A, b, c);
    }

    public static void main(String[] args) {

        StdOut.println("----- test 1 --------------------");
        test1();
        StdOut.println();

        StdOut.println("----- test 2 --------------------");
        test2();
        StdOut.println();

        StdOut.println("----- test 3 --------------------");
        test3();
        StdOut.println();

        StdOut.println("----- test 4 --------------------");
        test4();
        StdOut.println();

        StdOut.println("----- test random ---------------");
        int m = Integer.parseInt(StdIn.readLine());
        int n = Integer.parseInt(StdIn.readLine());
        double[] c = new double[n];
        double[] b = new double[m];
        double[][] A = new double[m][n];
        for (int j = 0; j < n; j++)
            c[j] = StdRandom.uniform(1000);
        for (int i = 0; i < m; i++)
            b[i] = StdRandom.uniform(1000);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = StdRandom.uniform(100);
//        Simplex lp = new Simplex(A, b, c);
        test(A, b, c);
    }
}
