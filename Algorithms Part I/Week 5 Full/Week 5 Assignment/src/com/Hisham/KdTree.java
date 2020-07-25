package com.Hisham;

import edu.princeton.cs.algs4.*;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.awt.Color;

public class KdTree {

    private int size = 0;
    private Point2D point = null;

    private Node root = null;

    private static class Node {
        Point2D point;
        RectHV rect;
        Node lb, rt;
        boolean vertical;

        private Node(Point2D point, boolean vertical, Node prev) {
            this.point = point;
            this.vertical = vertical;

            if (prev == null) {
                this.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            } else {
                double xmin = prev.rect.xmin();
                double xmax = prev.rect.xmax();
                double ymin = prev.rect.ymin();
                double ymax = prev.rect.ymax();

                int comp = prev.compareTo(point);
//                int comp = prev.vertical ? Double.compare(prev.point.x(), point.x()) : Double.compare(prev.point.y(), point.y());

                if (this.vertical) {
                    if (comp > 0) {
                        ymax = prev.point.y();
                    } else {
                        ymin = prev.point.y();
                    }
                } else {
                    if (comp > 0) {
                        xmax = prev.point.x();
                    } else {
                        xmin = prev.point.x();
                    }
                }

                this.rect = new RectHV(xmin, ymin, xmax, ymax);
            }
        }

        private int compareTo(Point2D that) {
            if (this.vertical) {
                return Double.compare(this.point.x(), that.x());
            } else {
                return Double.compare(this.point.y(), that.y());
            }
        }

        private void draw() {
            StdDraw.setPenRadius(.007);
            if (this.vertical) {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(this.point.x(), this.rect.ymin(), this.point.x(), this.rect.ymax());
            } else {
                StdDraw.setPenColor(Color.blue);
                StdDraw.line(this.rect.xmin(), this.point.y(), this.rect.xmax(), this.point.y());
            }
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(Color.black);
            point.draw();
            if (this.lb != null) {
                this.lb.draw();
            }
            if (this.rt != null) {
                this.rt.draw();
            }
        }

    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        root = insert(root, p, true, null);
    }

    private Node insert(Node h, Point2D point, boolean isVertical, Node prev) {
        if (h == null) {
            size++;
            return new Node(point, isVertical, prev);
        }
        if (h.point.compareTo(point) == 0) {
            return h;
        }
        int comp = h.compareTo(point);
        if (comp > 0) {
            h.lb = insert(h.lb, point, !isVertical, h);
        } else {
            h.rt = insert(h.rt, point, !isVertical, h);
        }
        return h;
    }

    private Point2D get(Point2D point) {
        return get(root, point);
    }

    private Point2D get(Node h, Point2D point) {
        if (h == null) {
            return null;
        }
        if (h.point.compareTo(point) == 0) {
            return h.point;
        }
        int comp = h.compareTo(point);
        if (comp > 0) {
            return get(h.lb, point);
        } else {
            return get(h.rt, point);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        return get(p) != null;
    }

    public void draw() {
        if (root != null) {
            root.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("null rectangle");
        }
        if (isEmpty()) {
            return null;
        }
        List<Point2D> list = new LinkedList<>();
        range(root, rect, list);
        return list;
    }

    private void range(Node h, RectHV rect, List<Point2D> list) {
        if (h == null) {
            return;
        }
        if (rect.intersects(h.rect)) {
            if (rect.contains(h.point)) {
                list.add(h.point);
            }
            range(h.lb, rect, list);
            range(h.rt, rect, list);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null point");
        }
        if (isEmpty()) {
            return null;
        }
        point = root.point;
        nearest(root, p);
        return point;
    }

    private void nearest(Node h, Point2D p) {
        if (h == null) {
            return;
        }
        if (point == null) {
            point = h.point;
        }
        if (h.rect.distanceSquaredTo(p) < p.distanceSquaredTo(point)) {
            if (p.distanceSquaredTo(h.point) < p.distanceSquaredTo(point)) {
                point = h.point;
            }
            if (h.compareTo(p) > 0) {   // p is less so first traverse the left than right
                nearest(h.lb, p);
                nearest(h.rt, p);
            } else {                   // p is greater so first traverse the right than left
                nearest(h.rt, p);
                nearest(h.lb, p);
            }
        }
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional)

        // time test
        String filename = StdIn.readLine();
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        System.out.println("size of input = "+kdtree.size());
        double x=StdIn.readDouble();
        double y=StdIn.readDouble();
        Point2D p=new Point2D(x,y);

        Instant instant=Instant.now();
        System.out.println(kdtree.nearest(p));
        Instant instant1=Instant.now();
        System.out.println("time taken for nearest neighbour in nanosecond = "+Duration.between(instant,instant1).toNanos());

        Instant instant2=Instant.now();
        System.out.println(kdtree.range(new RectHV(.23,.32,.78,.89)));
        Instant instant3=Instant.now();
        System.out.println("time taken for range search in nanosecond = "+Duration.between(instant2,instant3).toNanos());
    }
}
