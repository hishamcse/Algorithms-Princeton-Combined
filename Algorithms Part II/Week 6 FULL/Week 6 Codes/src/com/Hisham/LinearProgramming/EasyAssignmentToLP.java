package com.Hisham.LinearProgramming;

import edu.princeton.cs.algs4.LinearProgramming;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class EasyAssignmentToLP {

//    an n-by-n assignment problem (maximum weighted bipartite matching)
//    by reducing it to linear programming.

    public static void main(String[] args) {
        int n = Integer.parseInt(StdIn.readLine());

        double[] c = new double[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i * n + j] = StdRandom.uniform();
            }
        }

        double[] b = new double[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            b[i] = 1.0;
        }

        double[][] A = new double[2 * n][n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][i * n + j] = 1.0;
            }
        }
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                A[j + n][i * n + j] = 1.0;
            }
        }

        LinearProgramming lp = new LinearProgramming(A, b, c);

        // print weight of max weight perfect matching
        System.out.println(lp.value());

        // print max weight perfect matching
        double[] solve = lp.primal();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (solve[i * n + j] == 1.0) {
                    System.out.println(i + "-" + j);
                }
            }
        }
    }
}
