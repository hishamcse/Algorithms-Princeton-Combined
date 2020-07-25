package com.company;

public class EggDrop {
    //version 0: a simple loop checking for each floor starting from 0.if the egg breaks,T found
//    for(int i=0;i<n;i++){
//        if(breakegg[i]==true){
//            System.out.println("true for floor "+(i+1));
//        }
//    }

    //version 1:Use Binary search strategy.
    //Start from the middle floor, drop the egg
    //If it breaks, repeat with the lower half
    //Otherwise, repeat with the upper half
//    int lo=0,hi=n-1,t=n;
//    while(lo<hi){
//        int mid=lo+(hi-lo)/2;
//        if(breakegg[mid]==true){
//            hi=mid-1;
//            if(mid<t) {
//                t = mid;
//            }else{
//               return t;
//          }
//        }else{
//            lo=mid+1;
//        }
//    }
//    System.out.println("true for floor "+(t+1));

//    version 2:
//    Drop the at floor 1, 2, 4, 8, 16,… until it breaks
//    If the egg drop at level 32, that mean T must be between 16 and 32 (between the floor of last toss and the floor of this toss).
//    At this time, you have used 1 egg
//    lgT tosses because you double the floor number each time
//    Perform a binary search from floor 16 to 32.
//    The binary search will cost another lgT tosses
//    Because you do binary search on half of the floor (16 to 32 in this case, not all 32 floors), you need to use lgT - 1 eggs.
//    In total, it will take you ~lgT eggs and ~2lgT tosses.

    //version 3:
//    To make it easy to imagine, let’s take n = 100, so √n= 10
//    Drop the egg at
//    √n floor (level 10 in this case)
//    If it doesn’t break, increase the floor by
//    √n and repeat until the egg breaks
//    Until that, you have used maximum
//    √n= 10 tosses and 1 egg
//    Now you know the range that can make egg break. That range size is
//    √n For example, the egg breaks at the floor 60, that mean T must be between 50 and 60
//    Do a sequential search in that range, use the other remaining egg. Because the length of that range is
//    √n,it takes you maximum another
//    √n= 10 tosses
//    The total running time is 2√n and takes 2 eggs

    //version 4
//    We drop the egg at floor 1, 3(1+2), 6(1+2+3), 10(1+2+3+4), … Note that 1+2+3+4+…k=k(k+1)2~12k2. Now that T=12k2, k=2T−−−√.
//
//    From k(k−1)2 to k(k+1)2, we toss egg one by one, then we toss 12k2 times.
//
//    In total, we toss 2*(2T)^.5 times, thus c=2√2


}
