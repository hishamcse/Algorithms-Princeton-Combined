package com.Hisham.GeoApplicationofBST.LineSegmentIntersection;

public class SegmentHV implements Comparable<SegmentHV>{

    public final int x1,y1; //left endpoint
    public final int x2,y2; //right endpoint

    public SegmentHV(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public int compareTo(SegmentHV o) {
        if(this.y1<o.y1){ return -1;}
        if(this.y1>o.y1){ return 1;}
        if(this.y2<o.y2){ return -1;}
        if(this.y2>o.y2){ return 1;}
        if(this.x1<o.x1){ return -1;}
        if(this.x1>o.x1){ return 1;}
        if(this.x2<o.x2){ return -1;}
        if(this.x2>o.x2){ return 1;}
        return 0;
    }

    public boolean isHorizontal(){
        return this.y1==this.y2;
    }

    public boolean isVertical(){
        return this.x1==this.x2;
    }

    public String toString(){
        String s="";
        return s+"("+x1+","+y1+")"+","+"("+x2+","+y2+")";
    }

}
