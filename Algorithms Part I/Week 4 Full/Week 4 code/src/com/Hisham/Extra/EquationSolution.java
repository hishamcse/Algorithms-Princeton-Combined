package com.Hisham.Extra;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

import java.time.Duration;
import java.time.Instant;

public class EquationSolution{

    //solve for a+2*b^2=3*c^3+4*d^4
    private static class Left  implements Comparable<Left> {
        public double leftsum;
        public int i;
        public int j;

        public Left(int i, int j) {
            this.leftsum = i+Math.pow(j,2);
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(Left o) {
            return Double.compare(this.leftsum,o.leftsum);
        }
    }

        private static class Right implements Comparable<Right> {
        public double rightsum;
        public int i;
        public int j;

        public Right(int i, int j) {
            this.rightsum = 3*Math.pow(i,3)+4*Math.pow(j,4);
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(Right o) {
            return Double.compare(this.rightsum,o.rightsum);
        }
    }

    //O(n^2*logn)
    public void process(int n){
        MinPQ<Left> leftMinPQ=new MinPQ<>();
        MinPQ<Right> rightMinPQ=new MinPQ<>();

        for(int i=0;i<n;i++){
            leftMinPQ.insert(new Left(i,i));
            rightMinPQ.insert(new Right(i,i));
        }

        while (!leftMinPQ.isEmpty()){   //or,rightMinPQ.isEmpty()
            Left l=leftMinPQ.delMin();
            while (!rightMinPQ.isEmpty()) {
                Right r = rightMinPQ.delMin();

                if (l.leftsum == r.rightsum && l.i!=r.i && l.j!=r.j) {
                    System.out.println(l.i + "+" + l.j + "^2" + " = 3*" + r.i + "^3+" + "4*" + r.j + "^4");
                    break;
                }

                if (r.j < n) {
                    rightMinPQ.insert(new Right(r.i, r.j + 1));
                }
            }
            if(l.j<n){
                leftMinPQ.insert(new Left(l.i, l.j + 1));
            }
            for(int i=0;i<n;i++){
                rightMinPQ.insert(new Right(i,i));
            }
        }
    }

    public static void main(String[] args) {
        int n= StdIn.readInt();
        EquationSolution cubeSum=new EquationSolution();
        Instant i1=Instant.now();
        cubeSum.process(n);
        Instant i2=Instant.now();
        System.out.println("time="+ Duration.between(i1,i2).toMillis());
    }
}
