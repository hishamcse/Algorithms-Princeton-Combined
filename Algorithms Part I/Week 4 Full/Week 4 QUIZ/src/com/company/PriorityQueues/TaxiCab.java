package com.company.PriorityQueues;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

public class TaxiCab {

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
            if(this.cubicsum>o.cubicsum){
                return 1;
            }else if(this.cubicsum<o.cubicsum){
                return -1;
            }else if(this.i>o.i){
                return 1;
            }else if(this.i<o.i){
                return -1;
            }
            return 0;
        }
    }

    public void process(int n) {
        MinPQ<Cube> pq = new MinPQ<>();
        for (int i = 0; i < n; i++) {
            pq.insert(new Cube(Math.pow(i, 3) + Math.pow(i, 3), i, i));
        }
        Cube prev=new Cube(0,0,0);
        while (!pq.isEmpty()) {
            Cube c = pq.delMin();
            if(prev.cubicsum==c.cubicsum && prev.cubicsum!=0) {
                System.out.println(prev.i + "^3" + " + " + prev.j + "^3" + " = " +
                        c.i + "^3" + " + " + c.j + "^3");
            }
            if (c.j < n) {
                pq.insert(new Cube(Math.pow(c.i, 3) + Math.pow(c.j + 1, 3), c.i, c.j + 1));
            }
            prev=c;
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        TaxiCab cubeSum = new TaxiCab();
        cubeSum.process(n);
    }
}
