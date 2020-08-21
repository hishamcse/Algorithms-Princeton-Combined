package com.Hisham.DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class BruteSCC {
        private int count;    // number of strongly connected components
        private final int[] id;     // id[v] = id of strong component containing v

        public BruteSCC(Digraph G) {

            // initially each vertex is in its own component
            id = new int[G.V()];
            for (int v = 0; v < G.V(); v++)
                id[v] = v;

            // compute transitive closure
            TransitiveClosure tc = new TransitiveClosure(G);

            // if v and w are mutually reachable, assign v to w's component
            for (int v = 0; v < G.V(); v++)
                for (int w = 0; w < v; w++)
                    if (tc.reachable(v, w) && tc.reachable(w, v))
                        id[v] = id[w];

            // compute number of strongly connected components
            for (int v = 0; v < G.V(); v++)
                if (id[v] == v)
                    count++;
        }

        // return the number of strongly connected components
        public int count() { return count; }

        // are v and w strongly connected?
        public boolean stronglyConnected(int v, int w) {
            return id[v] == id[w];
        }

        // in which strongly connected component is vertex v?
        public int id(int v) { return id[v]; }


        public static void main(String[] args) {
            In in = new In(args[0]);
            Digraph G = new Digraph(in);
            BruteSCC scc = new BruteSCC(G);

            // number of connected components
            int m = scc.count();
            StdOut.println(m + " components");

            // compute list of vertices in each strong component
            Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
            for (int i = 0; i < G.V(); i++) {
                components[i] = new Queue<>();
            }
            for (int v = 0; v < G.V(); v++) {
                components[scc.id(v)].enqueue(v);
            }

            // print results
            for (int i = 0; i < m; i++) {
                for (int v : components[i]) {
                    StdOut.print(v + " ");
                }
                StdOut.println();
            }

        }

}
