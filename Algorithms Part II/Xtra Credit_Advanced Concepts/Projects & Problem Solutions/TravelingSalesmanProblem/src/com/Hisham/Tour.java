package com.Hisham;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;

public class Tour {

    private Node first, last;
    private int size;
    private double distance;

    private static class Node {
        Point p;
        Node next;

        public Node(Point p) {
            this.p = p;
            this.next = null;
        }
    }

    // create an empty tour
    public Tour() {
        first = null;
        last = null;
        size = 0;
        distance = 0.0;
    }

    // create a 4 point tour for debugging
    public Tour(Point a, Point b, Point c, Point d) {

        Node A = new Node(a);
        Node B = new Node(b);
        Node C = new Node(c);
        Node D = new Node(d);

        // link the nodes together in a tour
        A.next = B;
        B.next = C;
        C.next = D;
        D.next = null;

        // set the first and last nodes
        this.first = A;
        this.last = D;
    }

    // size of the tour
    public int size() {
        return size;
    }

    // adding the point to the tour
    private void add(Point p) {
        Node node = new Node(p);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
    }

    // adding point to specific position
    private void add(Point p, int index) {
        Node newNode = new Node(p);
        Node node = first;
        if (node == null) {
            first = newNode;
        } else {
            int i = 0;
            while (node != null) {
                if (i == index) {
                    newNode.next = node.next;
                    node.next = newNode;
                    break;
                }
                i++;
                node = node.next;
            }
        }
        size++;
    }

    // print the tour to standard output
    public void show() {
        Node node = first;
        while (node != null) {
            System.out.println(node.p);
            node = node.next;
        }
    }

    // draw the tour
    public void draw() {
        Node node = first;
        if (node == null) {
            return;
        }
        StdDraw.setPenRadius(.05);
        StdDraw.setPenColor(Color.black);
        node.p.draw();
        while (node.next != null) {
            int x = StdRandom.uniform(5);
            switch (x) {
                case 0:
                    StdDraw.setPenColor(Color.RED);
                    break;
                case 1:
                    StdDraw.setPenColor(Color.blue);
                    break;
                case 2:
                    StdDraw.setPenColor(Color.WHITE);
                    break;
                case 3:
                    StdDraw.setPenColor(Color.DARK_GRAY);
                    break;
                case 4:
                    StdDraw.setPenColor(Color.CYAN);
            }
            node.next.p.draw();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(.01);
            node.p.drawTo(node.next.p);
            node = node.next;
        }
    }

    // return the total distance of the tour
    public double distance() {
        Node node = first;
        while (node.next != null) {
            distance += node.p.distanceTo(node.next.p);
            node = node.next;
        }
        return distance;
    }

    // insert p using smallest insertion heuristic
    public void insertSmallest(Point p) {
        double min = -1;
        Node node = first;
        Node newNode = new Node(p);
        Node best = first;
        if (node == null || node.next == null || node.next.next == null) {
            add(p);
        } else {
            while (node.next != null) {
                double d1 = node.p.distanceTo(p);
                double d2 = p.distanceTo(node.next.p);
                double d3 = node.p.distanceTo(node.next.p);

                double t = d1 + d2 - d3;

                if (t < min || min < 0) {
                    min = t;
                    best = node;
                }
                node = node.next;
            }
            double d1 = first.p.distanceTo(p);
            double d2 = node.p.distanceTo(p);
            double d3 = node.p.distanceTo(first.p);

            double t = d1 + d2 - d3;
            if (t < min) {
                node.next = newNode;
            } else {
                newNode.next = best.next;
                best.next = newNode;
            }
        }
        size++;
    }

    // insert p using nearest neighbor heuristic
    public void insertNearest(Point p) {
        double distance, min = Double.MAX_VALUE;
        Node node = first;
        if (node == null) {
            add(p);
        } else {
            while (node != null) {
                distance = p.distanceTo(node.p);
                min = Math.min(distance, min);
                node = node.next;
            }
            node = first;
            int i = 0;
            while (node != null) {
                distance = p.distanceTo(node.p);
                if (min == distance) {
                    add(p, i);
                    break;
                }
                i++;
                node = node.next;
            }
        }
    }

}
