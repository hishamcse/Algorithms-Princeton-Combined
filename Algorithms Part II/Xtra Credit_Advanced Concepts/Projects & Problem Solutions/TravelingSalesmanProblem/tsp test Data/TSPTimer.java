/*************************************************************************
 *  YOU DO NOT NEED TO MODIFY THIS FILE
 *
 *  Compilation:  javac-introcs TSPTimer.java
 *  Execution:    java-introcs -Xint TSPTimer n
 *  Dependencies: Tour.java Point.java Stopwatch.java StdOut.java
 *
 *  Time the two heuristics by generated random instances of size n.
 *
 *  % java-introcs -Xint TSPTimer 1000
 *  Tour length = 26338.42949015926
 *  Nearest insertion:  0.056 seconds
 *
 *  Tour length = 15505.745750759515
 *  Smallest insertion:  0.154 seconds
 *
 *  The -Xint flag turns off various compiler optimizations, which
 *  helps normalize and stabilize the timing data that you collect.
 *
 *************************************************************************/

public class TSPTimer {

    public static void main(String[] args) {
        double lo = 0.0, hi = 600.0;
        int n = Integer.parseInt(args[0]);

        // generate data and run nearest insertion heuristic
        Stopwatch timer1 = new Stopwatch();
        Tour tour1 = new Tour();
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(lo, hi);
            double y = StdRandom.uniform(lo, hi);
            Point p = new Point(x, y);
            tour1.insertNearest(p);
        }
        double length1 = tour1.length();
        double elapsed1 = timer1.elapsedTime();
        StdOut.println("Tour length = " + length1);
        StdOut.println("Nearest insertion:  " + elapsed1 + " seconds");
        StdOut.println();


        // generate data and run smallest insertion heuristic
        Stopwatch timer2 = new Stopwatch();
        Tour tour2 = new Tour();
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(lo, hi);
            double y = StdRandom.uniform(lo, hi);
            Point p = new Point(x, y);
            tour2.insertSmallest(p);
        }
        double length2 = tour2.length();
        double elapsed2 = timer2.elapsedTime();
        StdOut.println("Tour length = " + length2);
        StdOut.println("Smallest insertion:  " + elapsed2 + " seconds");
    }

}
