package com.Hisham.EventDrivenSimulation.ParticleCollisionSystem;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

import java.awt.*;

public class CollisionSystem {

    private static final double HZ = 0.5;
    private MinPQ<Event> pq;
    private double t=0.0;
    private final Particle[] particles;

    public CollisionSystem(Particle[] particles){
        this.particles=particles.clone();
    }

    public void predict(Particle a,double limit){
        if(a==null)return;

        for(int i=0;i<particles.length;i++){
            double dt=a.timetoHit(particles[i]);
            if(t+dt<=limit){
                pq.insert(new Event(t+dt,a,particles[i]));
            }
        }

        double dty=a.timetoHitHorizontalWall();
        double dtx=a.timetoHitVerticalWall();
        if(t+dtx<=limit){
            pq.insert(new Event(t+dtx,a,null));
        }
        if(t+dty<=limit){
            pq.insert(new Event(t+dty,null,a));
        }
    }

    public void redraw(double limit){
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (t < limit) {
            pq.insert(new Event(t + 1.0 / HZ, null, null));
        }
    }

    public void simulation(double limit){
        pq=new MinPQ<>();
        for(int i=0;i<particles.length;i++){
            predict(particles[i],limit);
        }
        pq.insert(new Event(0,null,null));

        while (!pq.isEmpty()){
            Event event=pq.delMin();
            if(!event.isValid()){
                continue;
            }
            Particle a=event.getA();
            Particle b=event.getB();

            for(int i=0;i<particles.length;i++){
                particles[i].move(event.getTime()-t);
            }
            t=event.getTime();

            if      (a != null && b != null) a.bounceOff(b);              // particle-particle collision
            else if (a != null && b == null) a.bounceOffVerticalWall();   // particle-wall collision
            else if (a == null && b != null) b.bounceOffHorizontalWall(); // particle-wall collision
            else if (a == null && b == null) redraw(limit);               // redraw event

            predict(a, limit);
            predict(b, limit);
        }
    }

    public static void main(String[] args) {

        StdDraw.setCanvasSize(600, 600);

        // enable double buffering
        StdDraw.enableDoubleBuffering();

        // the array of particles
        Particle[] particles;

        // create n random particles
//        int n = StdIn.readInt();
//        particles = new Particle[n];
//        for (int i = 0; i < n; i++)
//            particles[i] = new Particle();


        // or read from standard input
            int n = StdIn.readInt();
            particles = new Particle[n];
            for (int i = 0; i < n; i++) {
                double rx = StdIn.readDouble();
                double ry = StdIn.readDouble();
                double vx = StdIn.readDouble();
                double vy = StdIn.readDouble();
                double radius = StdIn.readDouble();
                double mass = StdIn.readDouble();
                int r = StdIn.readInt();
                int g = StdIn.readInt();
                int b = StdIn.readInt();
                Color color = new Color(r, g, b);
                particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
            }

        // create collision system and simulate
        CollisionSystem system = new CollisionSystem(particles);
        system.simulation(10000);
    }

}
