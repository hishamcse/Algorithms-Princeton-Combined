package com.Hisham.MaxFlowMinCut;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdIn;

public class PerfectMatchingForKBipartite {

    public boolean solution(int[][] relationships) {
        int n = relationships.length;
        int s = n;
        int t = n + 1;

        // creating flowNetwork
        FlowNetwork flowNetwork = new FlowNetwork(n + 2);

        // men -> women relationships
        for (int v = 0; v < n / 2; v++) {
            for (int w = n / 2; w < n; w++) {
                if (relationships[v][w] == 0) continue;
                flowNetwork.addEdge(new FlowEdge(v, w, Double.POSITIVE_INFINITY));   // k relationships for men
            }
        }
        // women -> men relationships
        for (int w = n / 2; w < n; w++) {
            for (int v = 0; v < n / 2; v++) {
                if (relationships[w][v] == 0) continue;
                flowNetwork.addEdge(new FlowEdge(w, v, Double.POSITIVE_INFINITY));   // k relationships for women
            }
        }

        // connecting from source to men
        for (int v = 0; v < n / 2; v++) {
            flowNetwork.addEdge(new FlowEdge(s, v, 1));
        }
        // connecting from women to target
        for (int w = n / 2; w < n; w++) {
            flowNetwork.addEdge(new FlowEdge(w, t, 1));
        }

        FordFulkerson maxFlow = new FordFulkerson(flowNetwork, s, t);
        return (int) maxFlow.value() == n / 2;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] relationships = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                relationships[i][j] = StdIn.readInt();
            }
        }
        PerfectMatchingForKBipartite p = new PerfectMatchingForKBipartite();
        System.out.println(p.solution(relationships));
    }

    // test(true)
//     total 4 men & 4 women.2 relationships
//    [[0, 0, 1, 1], [0, 0, 1, 1], [0, 0, 0, 0], [0, 0, 0, 0]]

    // test 2(true)
//    total 6 men & 6 women,2 relationships
//    [[0, 0, 0, 1, 1, 1], [0, 0, 0, 1, 1, 1], [0, 0, 0, 1, 1, 1], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]]

    // test 3(true)
//    total 6 men & 6 women,3 relationships
//   [[0, 0, 0, 0, 1, 0, 0, 0], [0, 0, 0, 0, 0, 1, 0, 0], [0, 0, 0, 0, 0, 0, 1, 0], [0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0]]

}
