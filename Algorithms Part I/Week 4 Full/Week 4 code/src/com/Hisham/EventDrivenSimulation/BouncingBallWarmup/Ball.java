package com.Hisham.EventDrivenSimulation.BouncingBallWarmup;

import edu.princeton.cs.algs4.StdDraw;

public class Ball {

    private double rx,ry;
    private double vx,vy;
    private final double radius;

    public Ball(double k){
        rx=0.2+k*.02;
        ry=0.2+k*.02;
        vx=0.2;
        vy=0.2;
        radius=.01;
    }

    public void move(double dt){
        if((rx+vx*dt<radius) || (rx+vx*dt>1.0-radius)){
            vx=-vx;
        }
        if((ry+vy*dt<radius) || (ry+vy*dt>1.0-radius)){
            vy=-vy;
        }
        rx=rx+vx*dt;
        ry=ry+vy*dt;
    }

    public void draw(){
        StdDraw.filledCircle(rx,ry,radius);
    }

}
