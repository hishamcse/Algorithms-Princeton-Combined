package com.Hisham.GeoApplicationofBST.Interval1DSearch;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import java.util.HashSet;

//just a modification of LineSegmentIntersection Problem but easier than that problem
public class Interval1DIntersection {

    private static class Event implements Comparable<Event>{
        int time;
        Interval1D interval;

        public Event(int t,Interval1D Interval1D){
            this.time=t;
            this.interval=Interval1D;
        }

        @Override
        public int compareTo(Event o) {
            return Integer.compare(this.time, o.time);
        }
    }

    //this is known as Bentley Ottmann Sweep Line Algorithm
    public static void sweepLineAlgorithm(Interval1D[] segments){

        //event creation and adding to pq
        MinPQ<Event> pq=new MinPQ<>();
        for (Interval1D hv : segments) {
            Event e1 = new Event(hv.min, hv);
            Event e2=  new Event(hv.max,hv);
            pq.insert(e1);
            pq.insert(e2);
        }

        HashSet<Interval1D> st=new HashSet<>();

        while (!pq.isEmpty()){
            Event e=pq.delMin();
            int t=e.time;
            Interval1D segment=e.interval;
            if(t==segment.max){
                st.remove(segment);
            }else{
                for(Interval1D interval:st){
                    System.out.println("Intersection :"+segment+" with "+interval);
                }
                st.add(segment);
            }
        }
    }

    public static void main(String[] args) {
        int N= StdIn.readInt();
        Interval1D[] segments = new Interval1D[N];
        for (int i = 0; i < N; i++) {
            int left  = (int) (Math.random() * 1000);
            int right  = left + (int) (Math.random() * 10);
            segments[i] = new Interval1D(left,right);
            System.out.println(segments[i]);
        }
        System.out.println("Intersecting Segments:");
        sweepLineAlgorithm(segments);
    }
}
