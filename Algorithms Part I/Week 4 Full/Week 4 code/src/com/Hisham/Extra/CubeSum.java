package com.Hisham.Extra;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

public class CubeSum {

    private static class Cube implements Comparable<Cube> {
        public double cubicsum;
        public int i;
        public int j;

        public Cube(double cubicsum, int i, int j) {
            this.cubicsum = cubicsum;
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(Cube o) {
            return Double.compare(this.cubicsum,o.cubicsum);
        }
    }

    public void process(int n){
        MinPQ<Cube> pq=new MinPQ<>();
        for(int i=0;i<n;i++){
            pq.insert(new Cube(Math.pow(i, 3) + Math.pow(i, 3), i, i));
        }
        while (!pq.isEmpty()){
            Cube c=pq.delMin();
            System.out.println(c.cubicsum);
            if(c.j<n){
                pq.insert(new Cube(Math.pow(c.i, 3) + Math.pow(c.j + 1, 3), c.i, c.j + 1));
            }
        }
    }

    public static void main(String[] args) {
        int n= StdIn.readInt();
        CubeSum cubeSum=new CubeSum();
        cubeSum.process(n);
    }
}

//public class CubeSum implements Comparable<CubeSum> {
//    private final int sum;
//    private final int i;
//    private final int j;
//
//    public CubeSum(int i, int j) {
//        this.sum = i*i*i + j*j*j;
//        this.i = i;
//        this.j = j;
//    }
//
//    public int compareTo(CubeSum that) {
//        if (this.sum < that.sum) return -1;
//        if (this.sum > that.sum) return +1;
//        return 0;
//    }
//
//    public String toString() {
//        return sum + " = " + i + "^3" + " + " + j + "^3";
//    }
//
//
//    public static void main(String[] args) {
//
//        int n = Integer.parseInt(StdIn.readString());
//
//        // initialize priority queue
//        MinPQ<CubeSum> pq = new MinPQ<CubeSum>();
//        for (int i = 0; i <= n; i++) {
//            pq.insert(new CubeSum(i, i));
//        }
//
//        // find smallest sum, print it out, and update
//        while (!pq.isEmpty()) {
//            CubeSum s = pq.delMin();
//            StdOut.println(s);
//            if (s.j < n)
//                pq.insert(new CubeSum(s.i, s.j + 1));
//        }
//    }
//
//}

