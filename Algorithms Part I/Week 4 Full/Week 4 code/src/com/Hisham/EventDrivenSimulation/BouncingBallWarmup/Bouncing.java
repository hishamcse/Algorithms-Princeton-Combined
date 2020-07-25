package com.Hisham.EventDrivenSimulation.BouncingBallWarmup;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

public class Bouncing {

    public static void main(String[] args) {
        int n= StdIn.readInt();
        Ball[] balls=new Ball[n];
        for(int i=0;i<n;i++){
            balls[i]=new Ball(i);
        }
        while (true){
            StdDraw.clear();
            for(int i=0;i<n;i++){
                balls[i].move(.05);
                balls[i].draw();
            }
            StdDraw.show(50);
        }
    }

}
