package com.Hisham.Reduction;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class ConvexHull {     // graham scan algorithm

    Stack<Point2D> hull = new Stack<>();

    public ConvexHull(Point2D[] points) {
        Arrays.sort(points, Point2D.Y_ORDER);
        Arrays.sort(points, points[0].polarOrder());

        hull.push(points[0]);
//        hull.push(points[1]);   // there is a problem here.if points[0]==points[1].that's why the next step required

        int k1;
        for (k1 = 1; k1 < points.length; k1++)
            if (!points[0].equals(points[k1])) break;
        if (k1 == points.length) return;        // all points equal

        // find index k2 of first point not collinear with a[0] and a[k1]
        int k2;
        for (k2 = k1 + 1; k2 < points.length; k2++)
            if (Point2D.ccw(points[0], points[k1], points[k2]) != 0) break;

        hull.push(points[k2 - 1]);    // a[k2-1] is second extreme point

        for (int i = 2; i < points.length; i++) {
            Point2D p = hull.pop();
            while (Point2D.ccw(hull.peek(), p, points[i]) != 1) {
                p = hull.pop();
            }
            hull.push(p);
            hull.push(points[i]);
        }
    }

    public Stack<Point2D> getHull() {
        return hull;
    }

    public static void main(String[] args) {

        Point2D[] points = new Point2D[40];

        for (int i = 0; i < 40; i++) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            points[i] = new Point2D(x, y);
        }

        System.out.println("Points on the hull are:");

        ConvexHull convexHull = new ConvexHull(points);
        Stack<Point2D> st = convexHull.getHull();
        int n = st.size();
        for (int i = 0; i < n; i++) {
            Point2D p = st.pop();
            StdOut.println(p.x() + " " + p.y());
        }
    }
}
