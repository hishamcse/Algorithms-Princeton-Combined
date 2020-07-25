/* ******************************************************************************
 *  Compilation:  javac-algs4 Boid
 *  Execution:    none
 *  Dependencies: none
 *
 *  Implementation of a boid (birdoid). Each boid uses five rules to 
 *  decide where to go next. These are:
 *
 *  avoidCollision(nearest neighbors)
 *  avoidCollision(hawk)
 *  matchVelocity(nearest neighbors)
 *  plungeDeeper(nearest neighbors)
 *  returnToWorld(nearest neighbors)
 *  
 *  plungeDeeper models a Boid's desire to be deeper inside the flock.
 * 
 *  returnToWorld is a hack so that the Boids will eventually return to
 *  the origin, since that's where the camera is pointed. 
 * 
 *  More details in the code below.
 * 
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Vector;

public class Boid {    

    // Weights of a Boid's desires. Modify these and see what happens.

    private static final double BOID_AVOIDANCE_WEIGHT = 0.01;
    private static final double HAWK_AVOIDANCE_WEIGHT = 0.01;
    private static final double VELOCITY_MATCH_WEIGHT = 1;
    private static final double PLUNGE_DEEPER_WEIGHT = 1;
    private static final double RETURN_TO_ORIGIN_WEIGHT = 0.05;

    // Agiility of a Boid is given by this value. Increase and they can react
    // more quickly (and also have a higher max velocity, due to simplicity
    // of physics model).

    private static final double THRUST_FACTOR = 0.0001;



    // x,y stored as a Point2D
    // In the context of the Boid simulator, this is a little bit of
    // an awkward way to structure the code, since we map from Point2D 
    // to Boid -- i.e. they key is stored both in the symbol table
    // and in the value mapped to by the symbol table.

    // Despite this awkardness, this seems to be the best solution.

    private Point2D position;
    private Vector velocity;

    // create a boid at (x, y) with zero velocity
    public Boid(double x, double y) {
        position = new Point2D(x, y);
        velocity = new Vector(2);
    }

    public Boid(double x, double y, double xvel, double yvel) {
        position = new Point2D(x, y);
        velocity = new Vector(xvel, yvel);
    }

    public Point2D position() {
        return position;
    }

    public double x() {
        return position.x();
    }

    public double y() {
        return position.y();
    }

    // Each Boid tries to avoid collisions with its neighbors. This method
    // provides a thrust vector to achieve that goal.

    public Vector avoidCollision(Iterable<Boid> neighbors) {
        Vector requestedVector = new Vector(2);
        Vector myPosition = new Vector(x(), y());

        // Sum the difference in position between this boid and its nearest
        // neighbors. Scale each vector so that closer boids are given more
        // weight.

        for (Boid b : neighbors) {
            Vector neighborPosition = new Vector(b.x(), b.y());
            double distanceTo = myPosition.distanceTo(neighborPosition);
            // don't count self
            if (distanceTo == 0.0)
                break;

            Vector avoidanceVector = myPosition.minus(neighborPosition);
            Vector scaledAvoidanceVector = avoidanceVector.scale(1.0 / distanceTo);
            requestedVector = requestedVector.plus(scaledAvoidanceVector);
        }

        return requestedVector;
    }


    // Return a thrust vector to avoid a collision with the Hawk.

    public Vector avoidCollision(Hawk hawk) {
        Vector myPosition = new Vector(x(), y());
        Vector hawkPosition = new Vector(hawk.x(), hawk.y());
        double distanceTo = myPosition.distanceTo(hawkPosition);
        Vector avoidanceVector = myPosition.minus(hawkPosition);
        Vector scaledAvoidanceVector = avoidanceVector.scale(1.0 / distanceTo);
        return scaledAvoidanceVector;
    }

    // Return a thrust vector to match velocities with neighbors.

    public Vector matchVelocity(Iterable<Boid> neighbors) {
        Vector requestedVector = new Vector(2);
        for (Boid b : neighbors) {
            Vector neighborVelocity = b.getVelocity();
            Vector matchingVector = neighborVelocity.minus(velocity);            
            requestedVector = requestedVector.plus(matchingVector);
        }
        return requestedVector;        
    }

    // Return a thrust vector towards the center of the nearest neighbors.
    public Vector plungeDeeper(Iterable<Boid> neighbors) {
        Vector centroid = new Vector(2);
        double neighborCnt = 0;
        for (Boid b : neighbors) {
            Vector neighborPosition = new Vector(b.x(), b.y());
            centroid = centroid.plus(neighborPosition);            
            neighborCnt++;
        }

        centroid = centroid.scale(1.0 / neighborCnt);
        // Boid centroidPoint = new Boid(centroid.cartesian(0), centroid.cartesian(1));
        Vector myPosition = new Vector(x(), y());
        return centroid.minus(myPosition);
    }

    // Return a thrust vector towards the origin: 0.5, 0.5;

    public Vector returnToWorld() {
        Vector center = new Vector(0.5, 0.5);
        Vector myPosition = new Vector(x(), y());
        return center.minus(myPosition);
    }    

    // Combines all thrust vectors into a single vector.
    // Each is weighted by arbitrary hard coded weights.


    public Vector desiredAcceleration(Iterable<Boid> neighbors, Hawk hawk) {
        Vector avoidanceVector = avoidCollision(neighbors).scale(BOID_AVOIDANCE_WEIGHT);
        Vector hawkAvoidanceVector = avoidCollision(hawk).scale(HAWK_AVOIDANCE_WEIGHT);
        Vector matchingVector = matchVelocity(neighbors).scale(VELOCITY_MATCH_WEIGHT);
        Vector plungingVector = plungeDeeper(neighbors).scale(PLUNGE_DEEPER_WEIGHT);
        Vector returnVector = returnToWorld().scale(RETURN_TO_ORIGIN_WEIGHT);
        Vector desired = new Vector(2);
        desired = desired.plus(avoidanceVector);
        desired = desired.plus(hawkAvoidanceVector);
        desired = desired.plus(matchingVector);
        desired = desired.plus(plungingVector);
        desired = desired.plus(returnVector);
        if (desired.magnitude() == 0.0)
            return desired;
        return desired.direction().scale(THRUST_FACTOR);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(x(), y());
    }

    public Vector getVelocity() {
        return velocity;
    }    

    public String toString() {
        return "" + x() + " " + y() + " " + " " + velocity;
    }

    // Updates position and velocity using rules given above.
    public void updatePositionAndVelocity(Iterable<Boid> neighbors, Hawk hawk) {
        double x = x() + velocity.cartesian(0);
        double y = y() + velocity.cartesian(1);
        position = new Point2D(x, y);
        Vector desire = desiredAcceleration(neighbors, hawk);
        velocity = velocity.plus(desire);
    }

}
