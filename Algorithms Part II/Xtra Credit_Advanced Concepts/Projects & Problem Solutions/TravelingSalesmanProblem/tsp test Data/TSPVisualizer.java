/******************************************************************************
  *  Name:    Jeremie Lumbroso
  *  NetID:   lumbroso
  *  Precept: P99
  *
  *  Partner Name:    Donna Gabai
  *  Partner NetID:   dgabai
  *  Partner Precept: P99
  * 
  *  Description:  Implements an interactive client that builds a Tour using
  *                either the nearest heuristic (red) or the smallest heuristic
  *                (blue).
  *                
  *                Can be called with or without an input file to begin:
  *
  *                  java-introcs TSPVisualizer tsp1000.txt
  *
  *                Keyboard commands:
  *                  - n   toggle nearest heuristic tour
  *                  - s   toggle smallest heuristic tour
  *                  - m   toggle mouse up correction (what does this do... ?)
  *                  - q   quit (no!)
  *
  *  Dependencies: Point, StdOut, StdDraw
  ******************************************************************************/

import java.util.ArrayList;

public class TSPVisualizer {
    
    public static void main(String[] args) {
        
        int xscale = 512;
        int yscale = 512-70;
        
        StdDraw.setXscale(0, 512);
        StdDraw.setYscale(-70, 512-70);
        
        
        StdDraw.textLeft(50, 400, "Keyboard command:");
        StdDraw.textLeft(80, 380, "- n   toggle nearest neighbor heuristic tour");
        StdDraw.textLeft(80, 360, "- s   toggle smallest insertion heuristic tour");
        StdDraw.textLeft(80, 340, "- m   'draw mode'");
        StdDraw.textLeft(80, 320, "- q   quit");

        StdDraw.enableDoubleBuffering();
        
        Tour nearest  = new Tour();
        Tour smallest = new Tour();
        
        ArrayList<Point> points = new ArrayList<Point>();
        
        boolean redraw = false;
        
        boolean showingNearest = true;
        boolean showingSmallest = true;
        
        boolean mouseWasUp = true;
        boolean mouseCorrect = true;
        
        // initialize the two data structures with point from file
        if (args.length > 0) {
            String filename = args[0];
            In in = new In(filename);
            
            xscale = in.readInt();
            yscale = in.readInt();
            
            StdDraw.setXscale(0, xscale);
            StdDraw.setYscale(-70, yscale);
            
            // Print dimensions
            StdOut.println(xscale + " " + yscale);
            
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                
                // Print line with new points coordinates
                StdOut.println(x + " " + y);
                
                Point p = new Point(x, y);
                
                points.add(p);
                
                nearest.insertNearest(p);
                smallest.insertSmallest(p);
            }
            
            redraw = true;
        }
        else {
            // Print dimensions
            StdOut.println(xscale + " " + yscale);
        }
        
        
        // MAIN EVENT LOOP
        // ------------------------------------------------------------------
        while (true) {
            
            // check keyboard events
            if (StdDraw.hasNextKeyTyped()) {
                
                char key = StdDraw.nextKeyTyped();
                
                if (key == 'n') showingNearest = !showingNearest;
                if (key == 's') showingSmallest = !showingSmallest;
                if (key == 'm') mouseCorrect = !mouseCorrect;
                if (key == 'q') break;
                
                redraw = true;
            }
            
            // on mouse click: add new point to tours
            if (StdDraw.mousePressed() && (!mouseCorrect || mouseWasUp)) {
                mouseWasUp = false;
                
                // the location (x, y) of the mouse
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                
                Point p = new Point(x, y);
                
                points.add(p);
                
                // insert points in the tours
                nearest.insertNearest(p);
                smallest.insertSmallest(p);
                
                // Print line with new points coordinates
                StdOut.println(x + " " + y);
                
                // indicate that the frame needs to be redrawn
                redraw = true;
            }
            else
                mouseWasUp = !StdDraw.mousePressed();
            
            
            // when the frame needs to be refreshed
            if (redraw) {
                redraw = false;
                
                StdDraw.clear();
                
                // draw in red the nearest neighbor
                if (showingNearest) {
                    StdDraw.setPenRadius(0.004);
                    StdDraw.setPenColor(StdDraw.RED);
                    nearest.draw();
                }
                
                // draw in blue the smallest
                if (showingSmallest) {
                    StdDraw.setPenRadius(0.003);
                    StdDraw.setPenColor(StdDraw.BLUE);
                    smallest.draw();
                }
                
                // draw all of the points
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.005);
                for (Point p : points)
                    p.draw();
                
                // print captions
                StdDraw.textLeft(10, -10, "num points: " + points.size());
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.textLeft(10, -35, "nearest: " + nearest.length());
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.textLeft(10, -60, "smallest: " + smallest.length());
                StdDraw.setPenColor(StdDraw.BLACK);
                
                StdDraw.show();
                StdDraw.pause(50);
            }
        }
        System.exit(0);
    }
}
