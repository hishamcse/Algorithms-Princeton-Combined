package com.Hisham.LinearProgramming;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class TwoPhaseSimplex {

    private static final double EPSILON = 1.0E-8;

    private final double[][] a;   // tableaux
    // row m   = objective function
    // row m+1 = artificial objective function
    // column n to n+m-1 = slack variables
    // column n+m to n+m+m-1 = artificial variables

    private final int m;          // number of constraints
    private final int n;          // number of original variables

    private final int[] basis;    // basis[i] = basic variable corresponding to row i

    // sets up the simplex tableaux
    public TwoPhaseSimplex(double[][] A, double[] b, double[] c) {
        m = b.length;
        n = c.length;
        a = new double[m + 2][n + m + m + 1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = A[i][j];
        for (int i = 0; i < m; i++)
            a[i][n + i] = 1.0;
        for (int i = 0; i < m; i++)
            a[i][n + m + m] = b[i];
        for (int j = 0; j < n; j++)
            a[m][j] = c[j];

        // if negative RHS, multiply by -1
        for (int i = 0; i < m; i++) {
            if (b[i] < 0) {
                for (int j = 0; j <= n + m + m; j++)
                    a[i][j] = -a[i][j];
            }
        }

        // artificial variables form initial basis
        for (int i = 0; i < m; i++)
            a[i][n + m + i] = 1.0;
        for (int i = 0; i < m; i++)
            a[m + 1][n + m + i] = -1.0;
        for (int i = 0; i < m; i++)
            pivot(i, n + m + i);

        basis = new int[m];
        for (int i = 0; i < m; i++)
            basis[i] = n + m + i;

        // StdOut.println("before phase I");
        // show();

        phase1();

        // StdOut.println("before phase II");
        // show();

        phase2();

        // StdOut.println("after phase II");
        // show();

        // check optimality conditions
        assert check(A, b, c);
    }

    // run phase I simplex algorithm to find basic feasible solution
    private void phase1() {
        while (true) {

            // find entering column q
            int q = bland1();
            if (q == -1) break;  // optimal

            // find leaving row p
            int p = minRatioRule(q);
            assert p != -1 : "Entering column = " + q;

            // pivot
            pivot(p, q);

            // update basis
            basis[p] = q;
            // show();
        }
        if (a[m + 1][n + m + m] > EPSILON) throw new ArithmeticException("Linear program is infeasible");
    }

    // run simplex algorithm starting from initial basic feasible solution
    private void phase2() {
        while (true) {

            // find entering column q
            int q = bland2();
            if (q == -1) break;  // optimal

            // find leaving row p
            int p = minRatioRule(q);
            if (p == -1) throw new ArithmeticException("Linear program is unbounded");

            // pivot
            pivot(p, q);

            // update basis
            basis[p] = q;
        }
    }

    // lowest index of a non-basic column with a positive cost - using artificial objective function
    private int bland1() {
        for (int j = 0; j < n + m; j++)
            if (a[m + 1][j] > EPSILON) return j;
        return -1;  // optimal
    }

    // lowest index of a non-basic column with a positive cost
    private int bland2() {
        for (int j = 0; j < n + m; j++)
            if (a[m][j] > EPSILON) return j;
        return -1;  // optimal
    }


    // find row p using min ratio rule (-1 if no such row)
    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < m; i++) {
            // if (a[i][q] <= 0) continue;
            if (a[i][q] <= EPSILON) {
            } else if (p == -1) p = i;
            else if ((a[i][n + m + m] / a[i][q]) < (a[p][n + m + m] / a[p][q])) p = i;
        }
        return p;
    }

    // pivot on entry (p, q) using Gauss-Jordan elimination
    private void pivot(int p, int q) {

        // everything but row p and column q
        for (int i = 0; i <= m + 1; i++)
            for (int j = 0; j <= n + m + m; j++)
                if (i != p && j != q) a[i][j] -= a[p][j] * a[i][q] / a[p][q];

        // zero out column q
        for (int i = 0; i <= m + 1; i++)
            if (i != p) a[i][q] = 0.0;

        // scale row p
        for (int j = 0; j <= n + m + m; j++)
            if (j != q) a[p][j] /= a[p][q];
        a[p][q] = 1.0;
    }

    // return optimal objective value
    public double value() {
        return -a[m][n + m + m];
    }

    // return primal solution vector
    public double[] primal() {
        double[] x = new double[n];
        for (int i = 0; i < m; i++)
            if (basis[i] < n) x[basis[i]] = a[i][n + m + m];
        return x;
    }

    // return dual solution vector
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

    private boolean check(double[][] A, double[] b, double[] c) {
        return isPrimalFeasible(A, b) && isDualFeasible(A, c) && isOptimal(b, c);
    }

    // print tableaux
    public void show() {
        StdOut.println("m = " + m);
        StdOut.println("n = " + n);
        for (int i = 0; i <= m + 1; i++) {
            for (int j = 0; j <= n + m + m; j++) {
                StdOut.printf("%7.2f ", a[i][j]);
                if (j == n + m - 1 || j == n + m + m - 1) StdOut.print(" |");
            }
            StdOut.println();
        }
        StdOut.print("basis = ");
        for (int i = 0; i < m; i++)
            StdOut.print(basis[i] + " ");
        StdOut.println();
        StdOut.println();
    }


    public static void test(double[][] A, double[] b, double[] c) {
        TwoPhaseSimplex lp;
        try {
            lp = new TwoPhaseSimplex(A, b, c);
        } catch (ArithmeticException e) {
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

    // x0 = 12, x1 = 28, opt = 800
    public static void test1() {
        double[] c = {13.0, 23.0};
        double[] b = {480.0, 160.0, 1190.0};
        double[][] A = {
                {5.0, 15.0},
                {4.0, 4.0},
                {35.0, 20.0},
        };
        test(A, b, c);
    }

    // dual of test1():  x0 = 12, x1 = 28, opt = 800
    public static void test2() {
        double[] b = {-13.0, -23.0};
        double[] c = {-480.0, -160.0, -1190.0};
        double[][] A = {
                {-5.0, -4.0, -35.0},
                {-15.0, -4.0, -20.0}
        };
        test(A, b, c);
    }

    public static void test3() {
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

    // unbounded
    public static void test4() {
        double[] c = {2.0, 3.0, -1.0, -12.0};
        double[] b = {3.0, 2.0};
        double[][] A = {
                {-2.0, -9.0, 1.0, 9.0},
                {1.0, 1.0, -1.0, -2.0},
        };
        test(A, b, c);
    }

    // degenerate - cycles if you choose most positive objective function coefficient
    public static void test5() {
        double[] c = {10.0, -57.0, -9.0, -24.0};
        double[] b = {0.0, 0.0, 1.0};
        double[][] A = {
                {0.5, -5.5, -2.5, 9.0},
                {0.5, -1.5, -0.5, 1.0},
                {1.0, 0.0, 0.0, 0.0},
        };
        test(A, b, c);
    }

    // floating-point EPSILON needed in min-ratio test
    public static void test6() {
        double[] c = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        double[] b = {-0.9, 0.2, -0.2, -1.1, -0.7, -0.5, -0.1, -0.1, -1};
        double[][] A = {
                {-2, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, -2, -1, 0, 0, 0, 0, 0, 0},
                {0, -1, -2, -1, 0, 0, 0, 0, 0},
                {0, 0, -1, -2, -1, -1, 0, 0, 0},
                {0, 0, 0, -1, -2, -1, 0, 0, 0},
                {0, 0, 0, -1, -1, -2, -1, 0, 0},
                {0, 0, 0, 0, 0, -1, -2, -1, 0},
                {0, 0, 0, 0, 0, 0, -1, -2, -1},
                {0, 0, 0, 0, 0, 0, 0, -1, -2}
        };
        test(A, b, c);
    }

    // test client
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
        StdOut.println("----- test 5 --------------------");
        test5();

        StdOut.println();
        StdOut.println("----- test 6 --------------------");
        test6();

        StdOut.println("----- test random ---------------");
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        double[] c = new double[n];
        double[] b = new double[m];
        double[][] A = new double[m][n];
        for (int j = 0; j < n; j++)
            c[j] = StdRandom.uniform(1000);
        for (int i = 0; i < m; i++)
            b[i] = StdRandom.uniform(1000) - 200;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = StdRandom.uniform(100) - 20;
        test(A, b, c);

    }
}
