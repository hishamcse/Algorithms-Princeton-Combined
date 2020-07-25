package com.Hisham.Extra;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

public class PowerNumber {
    //calculate all 2^i*5^j numbers
    private static class Power implements Comparable<Power> {
        public double sum;
        public int i;
        public int j;

        public Power(int i, int j) {
            this.sum = Math.pow(2,i)*Math.pow(5,j);
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(Power o) {
            return Double.compare(this.sum,o.sum);
        }
    }

    public void process(int n){
        MinPQ<Power> pq=new MinPQ<>();
        for(int i=0;i<n;i++){
            pq.insert(new Power(i, i));
        }
        while (!pq.isEmpty()){
            Power c=pq.delMin();
            System.out.println(c.sum);
            if(c.j<n){
                pq.insert(new Power(c.i, c.j + 1));
            }
        }
    }

    public static void main(String[] args) {
        int n= StdIn.readInt();
        PowerNumber cubeSum=new PowerNumber();
        cubeSum.process(n);
    }
}
