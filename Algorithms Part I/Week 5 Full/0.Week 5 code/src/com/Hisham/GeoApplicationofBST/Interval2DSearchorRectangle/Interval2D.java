package com.Hisham.GeoApplicationofBST.Interval2DSearchorRectangle;

import com.Hisham.GeoApplicationofBST.Interval1DSearch.Interval1D;

public class Interval2D{

    public Interval1D intervalX;
    public Interval1D intervalY;

    public Interval2D(Interval1D intervalX, Interval1D intervalY) {
        this.intervalX = intervalX;
        this.intervalY = intervalY;
    }

    public boolean contains(int x,int y){
        return intervalX.contains(x) && intervalY.contains(y);
    }

    public boolean intersects(Interval2D that){
        if(!this.intervalX.intersects(that.intervalX)){ return false;}
        if(!this.intervalY.intersects(that.intervalY)){ return false;}
        return true;
    }

    public String toString() {
        return intervalX + " x " + intervalY;
    }
}
