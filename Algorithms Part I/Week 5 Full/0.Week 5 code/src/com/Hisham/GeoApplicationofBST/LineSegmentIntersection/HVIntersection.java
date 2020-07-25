package com.Hisham.GeoApplicationofBST.LineSegmentIntersection;

import com.Hisham.GeoApplicationofBST.RangeSearch.RangeSearch;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

public class HVIntersection {

    private static class Event implements Comparable<Event>{
        int time;
        SegmentHV segmentHV;

        public Event(int t,SegmentHV segmentHV){
            this.time=t;
            this.segmentHV=segmentHV;
        }

        @Override
        public int compareTo(Event o) {
            return Integer.compare(this.time, o.time);
        }
    }

    //this is known as Bentley Ottmann Sweep Line Algorithm
    public static void sweepLineAlgorithm(SegmentHV[] segments){

        //event creation and adding to pq
        MinPQ<Event> pq=new MinPQ<>();
        for (SegmentHV hv : segments) {
            if (hv.isVertical()) {
                Event e = new Event(hv.x1, hv);
                pq.insert(e);
            } else if (hv.isHorizontal()) {
                Event e1 = new Event(hv.x1, hv);
                Event e2 = new Event(hv.x2, hv);
                pq.insert(e1);
                pq.insert(e2);
            }
        }

        RangeSearch<SegmentHV> st=new RangeSearch<>();

        while (!pq.isEmpty()){
            Event e=pq.delMin();
            int t=e.time;
            SegmentHV segment=e.segmentHV;
            if(segment.isVertical()){
                SegmentHV s1=new SegmentHV(-Integer.MAX_VALUE,segment.y1,-Integer.MAX_VALUE,segment.y1);
                SegmentHV s2=new SegmentHV(Integer.MAX_VALUE,segment.y2,Integer.MAX_VALUE,segment.y2);
                Iterable<SegmentHV> iterable=st.keysInOrder(s1,s2);
                for(SegmentHV segmentHV:iterable){
                    System.out.println("Intersection :"+segment+" with "+segmentHV);
                }
            }else if(t==segment.x1){
                st.put(segment);
            }else if(t==segment.x2){
                st.delete(segment);
            }
        }
    }

    public static void main(String[] args) {
        int N= StdIn.readInt();
        SegmentHV[] segments = new SegmentHV[N];
        for (int i = 0; i < N; i++) {
            int x1  = (int) (Math.random() * 10);
            int x2  = x1 + (int) (Math.random() * 3);
            int y1  = (int) (Math.random() * 10);
            int y2  = y1 + (int) (Math.random() * 5);
            if (Math.random() < 0.5) segments[i] = new SegmentHV(x1, y1, x1, y2);
            else                     segments[i] = new SegmentHV(x1, y1, x2, y1);
            System.out.println(segments[i]);
        }
        System.out.println("Intersecting Segments:");
        sweepLineAlgorithm(segments);
    }

}
