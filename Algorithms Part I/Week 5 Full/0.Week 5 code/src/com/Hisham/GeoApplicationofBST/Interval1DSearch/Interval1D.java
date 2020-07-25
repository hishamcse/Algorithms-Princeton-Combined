package com.Hisham.GeoApplicationofBST.Interval1DSearch;

public class Interval1D implements Comparable<Interval1D> {

    public int min;  //min endpoint
    public int max;

    public Interval1D(int min, int max) {
        if(min<=max) {
            this.min = min;
            this.max = max;
        }
    }

    public boolean contains(int x){
        return (x>=min) && (x<=max);
    }

    public boolean intersects(Interval1D that){
        if(this.max<that.min){ return false;}
        if(that.max<this.min){ return false;}
        return true;
    }


    @Override
    public int compareTo(Interval1D that) {
        if(this.min<that.min){ return -1;}
        if(this.min>that.min){ return 1;}
        if(this.max<that.max){ return -1;}
        if(this.max>that.max){ return 1;}
        return 0;
    }

    public String toString() {
        return "[" + min + ", " + max + "]";
    }
}
