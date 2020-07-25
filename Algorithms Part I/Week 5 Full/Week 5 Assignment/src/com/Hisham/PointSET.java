package com.Hisham;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.time.Duration;
import java.time.Instant;

public class PointSET {

    private final SET<Point2D> all;

    public PointSET() {                         // construct an empty set of points
        all = new SET<>();
    }

    public boolean isEmpty() {                   // is the set empty?
        return all.isEmpty();
    }

    public int size() {                         // number of points in the set
        return all.size();
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        if (!all.contains(p)) {
            all.add(p);
        }
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        return all.contains(p);
    }

    public void draw() {                        // draw all points to standard draw
        for (Point2D p : all) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {         // all points that are inside the rectangle (or on the boundary)
        if (rect == null) {
            throw new IllegalArgumentException("null rectangle");
        }
        SET<Point2D> list = new SET<>();
        for (Point2D p : all) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        if (all.isEmpty()) {
            return null;
        }
        Point2D n = null;
        for (Point2D q : all) {
            if(n==null){
                n=q;
                continue;
            }
            if (p.distanceSquaredTo(q) < p.distanceSquaredTo(n)) {
                n = q;
            }
        }
        return n;
    }

    public static void main(String[] args) {              // unit testing of the methods (optional)

        //time test
        String filename = StdIn.readLine();
        In in = new In(filename);
        PointSET tree = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            tree.insert(p);
        }
        System.out.println("size of input = "+tree.size());
        double x=StdIn.readDouble();
        double y=StdIn.readDouble();
        Point2D p=new Point2D(x,y);

        Instant instant=Instant.now();
        System.out.println(tree.nearest(p));
        Instant instant1=Instant.now();
        System.out.println("time taken for nearest neighbour in nanosecond = "+ Duration.between(instant,instant1).toNanos());

        Instant instant2=Instant.now();
        System.out.println(tree.range(new RectHV(.23,.32,.78,.89)));
        Instant instant3=Instant.now();
        System.out.println("time taken for range search in nanosecond = "+Duration.between(instant2,instant3).toNanos());
    }
}
