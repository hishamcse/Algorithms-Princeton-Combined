package com.company;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocialNetwork {

    private WeightedQuickUnionUF uf;
    private int total;

    //just assuming i as time

    public SocialNetwork(int n,int m){
        uf=new WeightedQuickUnionUF(n);
        total=0;
        List<Log> logs=new ArrayList<>();
        for(int i=0;i<m;i++) {
            int a = StdRandom.uniform(n);
            int b = StdRandom.uniform(n);
            if(connection(a,b)) {
                logs.add(new Log(i, a, b));
                if(allconnected(n)){
                    System.out.println(i);
                    break;
                }
            }else {
                i--;
            }
        }
    }

    public static class Log{
        private int timestamp;
        private int friend1;
        private int friend2;

        public Log(int timestamp, int friend1, int friend2) {
            this.timestamp = timestamp;
            this.friend1 = friend1;
            this.friend2 = friend2;
        }
    }

    public boolean connection(int a,int b){
        if(uf.find(a)!=uf.find(b)){
            uf.union(a,b);
            total++;
            return true;
        }
        return false;
    }

    public boolean allconnected(int n){
        if(total==n-1){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int m=scanner.nextInt();
        SocialNetwork socialNetwork=new SocialNetwork(n,m);

    }
}
