package com.Hisham.EventDrivenSimulation.ParticleCollisionSystem;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;

public class Particle {

    private double rx,ry;
    private double vx,vy;
    private final double radius;
    private final double mass;
    private int count;
    private final Color color;

    public Particle() {
        rx     = StdRandom.uniform(0.0, 1.0);
        ry     = StdRandom.uniform(0.0, 1.0);
        vx     = StdRandom.uniform(-0.005, 0.005);
        vy     = StdRandom.uniform(-0.005, 0.005);
        radius = 0.02;
        mass   = 0.5;
        color  = Color.BLACK;
    }

    public Particle(double rx, double ry, double vx, double vy, double radius, double mass, Color color) {
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = color;
    }

    public void move(double dt){
        rx=rx+vx*dt;
        ry=ry+vy*dt;
    }

    public void draw(){
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(rx,ry,radius);
    }

    public int count(){
        return count;
    }

    //prediction
    public double timetoHit(Particle that){
        if(this==that){
            return Double.POSITIVE_INFINITY;
        }
        double drx=that.rx-this.rx;
        double dry=that.ry-this.ry;
        double dvx=that.vx-this.vx;
        double dvy=that.vy-this.vy;
        double dvdr=dvx*drx+dvy*dry;
        if(dvdr>0){
            return Double.POSITIVE_INFINITY;
        }
        double dvdv=dvx*dvx+dvy*dvy;
        if(dvdv==0){
            return Double.POSITIVE_INFINITY;
        }
        double drdr=drx*drx+dry*dry;
        double sigma=this.radius+that.radius;
        double d=(dvdr*dvdr)-(dvdv*(drdr-(sigma*sigma)));
        if(d<0){
            return Double.POSITIVE_INFINITY;
        }
        return -(dvdr+Math.sqrt(d))/(dvdv);
    }

    public double timetoHitHorizontalWall(){
        if(vy>0) {
            return (1.0 - radius - ry) / vy;
        }else if(vy<0){
            return (radius-ry)/vy;
        }else{
            return Double.POSITIVE_INFINITY;
        }
    }

    public double timetoHitVerticalWall(){
        if(vx>0) {
            return (1.0 - radius - rx) / vx;
        }else if(vx<0){
            return (radius-rx)/vx;
        }else{
            return Double.POSITIVE_INFINITY;
        }
    }

    //simulation
    public void bounceOff(Particle that){
        double drx=that.rx-this.rx;
        double dry=that.ry-this.ry;
        double dvx=that.vx-this.vx;
        double dvy=that.vy-this.vy;
        double dvdr=dvx*drx+dvy*dry;
        double sigma=this.radius+that.radius;
        double J=(2*this.mass*that.mass*dvdr)/(sigma*(this.mass+that.mass));
        double Jx=(J*drx)/sigma;
        double Jy=(J*dry)/sigma;
        this.vx+=Jx/this.mass;
        that.vx-=Jx/that.mass;
        this.vy+=Jy/this.mass;
        that.vy-=Jy/that.mass;
        this.count++;
        that.count++;
    }

    public void bounceOffHorizontalWall(){
        vy=-vy;
        count++;
    }

    public void bounceOffVerticalWall(){
        vx=-vx;
        count++;
    }

    public double kineticEnergy() {
        return 0.5 * mass * (vx*vx + vy*vy);
    }

}
