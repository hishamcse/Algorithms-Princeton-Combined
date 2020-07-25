package com.company.UnionFind;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

import java.awt.*;

public class DerivedJavaAPI {

    public static void main(String[] args) {
//	    int n= StdIn.readInt();
//        StdOut.print(n);
        StdDraw.setPenColor(Color.red);
        StdDraw.line(0,0,1,1);
        StdDraw.circle(.5,.5,0.5);
        StdDraw.square(0,0,0.5);
        StdDraw.text(.5,.5,"hello world princeton");

        int N = 100;
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N*N);
        StdDraw.setPenRadius(.01);
        for (int i = 1; i <= N; i++)
        {
            StdDraw.point(i, i);    //O(n)
            StdDraw.point(i, i*i);  //O(n^2)
            StdDraw.point(i, i*Math.log(i));  //(O(log(N)))
        }

    }
}
