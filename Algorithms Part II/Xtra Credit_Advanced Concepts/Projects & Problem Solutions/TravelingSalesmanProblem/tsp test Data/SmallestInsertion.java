/*************************************************************************
 *  YOU DO NOT NEED TO MODIFY THIS FILE
 *
 *  Compilation:  javac SmallestInsertion.java
 *  Execution:    java SmallestInsertion < file.txt
 *  Dependencies: Tour.java Point.java StdIn.java StdDraw.java
 *
 *  Run smallest insertion heuristic for traveling salesperson problem
 *  and plot results.
 *
 *  % java SmallestInsertion < tsp1000.txt
 *
 *************************************************************************/

public class SmallestInsertion {

    public static void main(String[] args) {

        // get dimensions
        int width = StdIn.readInt();
        int height = StdIn.readInt();
        int border = 20;
        StdDraw.setCanvasSize(width, height + border);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(-border, height);

        // turn on double buffering
        StdDraw.enableDoubleBuffering();

        // run smallest insertion heuristic
        Tour tour = new Tour();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            tour.insertSmallest(p);

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
