package com.company.ConvexHull;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class ConvexHull {
    Stack<Point2D> hull=new Stack<>();

    public ConvexHull(Point2D[] points){
        Arrays.sort(points, Point2D.Y_ORDER);
        Arrays.sort(points, points[0].POLAR_ORDER);

        hull.push(points[0]);
        hull.push(points[1]);

        for(int i=2;i<points.length;i++){
            Point2D p=hull.pop();
            while(Point2D.ccw(hull.peek(),p,points[i])!=1){
                p=hull.pop();
            }
            hull.push(p);
            hull.push(points[i]);
        }
    }

    public Stack<Point2D> getHull() {
        return hull;
    }

    public static void main(String[] args) {

        Point2D []points=new Point2D[40];

        for(int i=0;i<40;i++){
            double x= StdIn.readDouble();
            double y=StdIn.readDouble();
            points[i]=new Point2D(x,y);
        }

        System.out.println("Points on the hull are:");

        ConvexHull convexHull=new ConvexHull(points);
        Stack<Point2D> st=convexHull.getHull();
        int n=st.size();
        for(int i=0;i<n;i++){
            Point2D p=st.pop();
            StdOut.println(p.getX()+" "+p.getY());
        }
    }
}
