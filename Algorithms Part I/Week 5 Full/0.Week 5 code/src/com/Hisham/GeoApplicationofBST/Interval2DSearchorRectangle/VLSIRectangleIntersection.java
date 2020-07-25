package com.Hisham.GeoApplicationofBST.Interval2DSearchorRectangle;

import com.Hisham.GeoApplicationofBST.Interval1DSearch.Interval1D;
import com.Hisham.GeoApplicationofBST.Interval1DSearch.IntervalST;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class VLSIRectangleIntersection {  //interval2D Intersection

    private static class Event implements Comparable<Event>{
        int time;
        Interval2D interval;

        public Event(int t,Interval2D interval){
            this.time=t;
            this.interval=interval;
        }

        @Override
        public int compareTo(Event o) {
            return Integer.compare(this.time, o.time);
        }
    }

    //this is known as Bentley Ottmann Sweep Line Algorithm
    public static void sweepLineAlgorithm(Interval2D[] segments){

        //event creation and adding to pq
        MinPQ<Event> pq=new MinPQ<>();
        for (Interval2D hv : segments) {
            Event e1 = new Event(hv.intervalX.min, hv);
            Event e2=  new Event(hv.intervalX.max,hv);
            pq.insert(e1);
            pq.insert(e2);
        }

        IntervalST<Interval2D> st=new IntervalST<>();

        while (!pq.isEmpty()){
            Event e=pq.delMin();
            int t=e.time;
            Interval2D rect=e.interval;
            if(t==rect.intervalX.max){
                st.delete(rect.intervalY);
            }else{
                for(Interval1D interval:st.searchAll(rect.intervalY)){
                    System.out.println("Intersection :"+rect+" with "+st.get(interval));
                }
                st.put(rect.intervalY,rect);
            }
        }
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();

        // generate N random intervals
        Interval2D[] rects = new Interval2D[N];
        for (int i = 0; i < N; i++) {
            int xmin = (int) (100 * Math.random());
            int ymin = (int) (100 * Math.random());
            int xmax = xmin + (int) (10 * Math.random());
            int ymax = ymin + (int) (20 * Math.random());
            rects[i] = new Interval2D(new Interval1D(xmin, xmax), new Interval1D(ymin, ymax));
            StdOut.println(rects[i]);
        }
        StdOut.println();
        sweepLineAlgorithm(rects);
    }
}
