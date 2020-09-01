/*************************************************************************
 *  YOU DO NOT NEED TO MODIFY THIS FILE
 *
 *  Compilation:  javac NearestInsertion.java
 *  Execution:    java NearestInsertion < file.txt
 *  Dependencies: Tour.java Point.java StdIn.java StdDraw.java
 *
 *  Run nearest neighbor insertion heuristic for traveling
 *  salesperson problemand plot results.
 *
 *  % java NearestInsertion < tsp1000.txt
 *
 *************************************************************************/

public class NearestInsertion {

    public static void main(String[] args) {

        // get dimensions
        int width = StdIn.readInt();
        int height = StdIn.readInt();
        int border = 20;
        StdDraw.setCanvasSize(width, height + border);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(-border, height);

        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        // run smallest insertion heuristic
        Tour tour = new Tour();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            tour.insertNearest(p);

            // uncomment the 4 lines below to animate
            // StdDraw.clear();
            // tour.draw();
            // StdDraw.textLeft(20, 0, "length = " + tour.length());
            // StdDraw.show();
            // StdDraw.pause(50);
        }

        // draw to standard draw
        tour.draw();
        StdDraw.show();
        
        // print tour to standard output
        StdOut.println(tour);
        StdOut.printf("Tour length = %.4f\n", tour.length());
        StdOut.printf("Number of points = %d\n", tour.size());
    }

}
