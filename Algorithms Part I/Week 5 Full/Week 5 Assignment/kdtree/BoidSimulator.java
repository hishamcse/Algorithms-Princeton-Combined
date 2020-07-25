/* *****************************************************************************
 *  Compilation:  javac-algs4 BoidSimulator.java
 *  Execution:    java-algs4 BoidSimuator
 *  Dependencies: KdTreeST.java Boid.java Hawk.java
 *
 *  Implementation of a boid simulator using the KdTreeST data type,
 *  supplemented with the method nearest(Point2D p, int k).
 *
 *  Note: This code is a bit hacked together. Apologies for any messy
 *  code. Interactivity features and other tweaks by Evan Sparano (Fall 2013).
 *  
 *  Instructions for using the boid simulator:
 *      Press "o" to zoom out.
 *      Press "i" to zoom in.
 *      Press "t" to track the center of mass of all boids.
 *      Press "h" to track the hawk.
 *      Press "m" to manually control the camera.
 *      While in "manual" mode, use arrow keys to control camera movement.
 *
 **************************************************************************** */

import java.awt.event.KeyEvent;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class BoidSimulator {
    
    // mode selection constants
    private static final char MANUAL_MODE   = 'm'; 
    private static final char TRACKING_MODE = 't';
    private static final char HAWK_MODE     = 'h';
    
    // camera movement constants
    private static final double ZOOM_FACTOR = 1.1;
    private static final double CAMERA_SPEED = 0.05;
            
    private static char mode = TRACKING_MODE; // start in "tracking" mode
    
    private static Iterable<Boid> lookUpBoids(KdTreeST<Boid> bkd, Iterable<Point2D> points) {
        Queue<Boid> values = new Queue<Boid>();
        for (Point2D p : points) {
            values.enqueue(bkd.get(p));
        }
        return values;
    }
    
    public static void main(String[] args) {  
        Hawk hawk = new Hawk(0.5, 0.3);
        int NUM_BOIDS = 1000;
        
        // Each boid tracks a number of nearest neighbors equal to FRIENDS
        int FRIENDS = 10;
        Boid[] boids = new Boid[NUM_BOIDS];
        double radius = 0.5;
        double currentX = 0.5;
        double currentY = 0.5;
        
        // Generate random boids.
        for (int i = 0; i < NUM_BOIDS; i++) {
            double startX = StdRandom.uniform();
            double startY = StdRandom.uniform();
            double velX = (StdRandom.uniform() - 0.5)/1000;
            double velY = (StdRandom.uniform() - 0.5)/1000;
            boids[i] = new Boid(startX, startY, velX, velY);
        }
        
        StdDraw.enableDoubleBuffering();
        while (true) {
            // process keyboard input
            if (StdDraw.isKeyPressed(KeyEvent.VK_I)) // press "i" to zoom in 
                radius *= 1/ZOOM_FACTOR;
            if (StdDraw.isKeyPressed(KeyEvent.VK_O)) // press "o" to zoom out
                radius *= ZOOM_FACTOR;
            if (StdDraw.isKeyPressed(KeyEvent.VK_M)) // press "m" to enter 
                mode = MANUAL_MODE;                  // "manual" mode
            if (StdDraw.isKeyPressed(KeyEvent.VK_H)) // press "h" to enter 
                mode = HAWK_MODE;                    // "hawk" mode
            if (StdDraw.isKeyPressed(KeyEvent.VK_T)) // press "t" to enter 
                mode = TRACKING_MODE;                // "tracking" mode
            
            // scale pen radius relative to zoom 
            StdDraw.setPenRadius(0.01*(0.5/radius));
            StdDraw.setXscale(currentX - radius, currentX + radius);
            StdDraw.setYscale(currentY - radius, currentY + radius);
            
            // draw all boids and calculate their meanX and meanY
            double meanX = 0.0;
            double meanY = 0.0;
            for (int i = 0; i < NUM_BOIDS; i++) {
                meanX += boids[i].x()/NUM_BOIDS;
                meanY += boids[i].y()/NUM_BOIDS;
                boids[i].draw();
            }
            
            // draw the hawk
            hawk.draw();
            
            // follow center of mass in tracking mode
            if (mode == TRACKING_MODE) { 
                currentX = meanX;
                currentY = meanY;
            } 
            // allow user to control movement in manual mode
            else if (mode == MANUAL_MODE) {
                // press "up arrow" to pan upwards
                if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) 
                    currentY += radius*CAMERA_SPEED;
                // press "down arrow" to pan downwards
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
                    currentY -= radius*CAMERA_SPEED;
                // press "left arrow" to pan to the left
                if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
                    currentX -= radius*CAMERA_SPEED;
                // press "right arrow" to pan to the right
                if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
                    currentX += radius*CAMERA_SPEED;
            } 
            // follow hawk in hawk mode
            else if (mode == HAWK_MODE) {
                currentX = hawk.x();
                currentY = hawk.y();
            }
            
            // The entire KdTree must be rebuilt every frame. Since the boids
            // are random, we expect a roughly balanced tree, despite the
            // lack of balancing in KdTreeST.
            
            KdTreeST<Boid> bkd = new KdTreeST<Boid>();
            for (int i = 0; i < NUM_BOIDS; i++) {
                bkd.put(boids[i].position(), boids[i]);
            }
            
            for (int i = 0; i < NUM_BOIDS; i++) {
                Iterable<Point2D> kNearestPoints = bkd.nearest(boids[i].position(), FRIENDS);
                Iterable<Boid> kNearest = lookUpBoids(bkd, kNearestPoints);
                boids[i].updatePositionAndVelocity(kNearest, hawk);
            }
            
            // The hawk will chase the nearest boid.
            Boid closestBoid = bkd.get(bkd.nearest(hawk.position()));
            hawk.updatePositionAndVelocity(closestBoid);
            
            // hawk.updatePositionAndVelocity(bkd.nearest(new Boid(hawk.x(), hawk.y())));
            StdDraw.show();
            StdDraw.pause(20);
            StdDraw.clear();
        }
    }
}
