package com.company.QuickSort;

import org.junit.Test;

import java.security.SecureRandom;

import static org.junit.Assert.*;

public class NutsAndBoltsTest {

    @Test
    public void sort() {
        SecureRandom random=new SecureRandom();
        int N = 1000;

//        Comparable[] nuts = {'@', '#', '$', '%', '^', '&'};
//        Comparable[] bolts = {'$', '%', '&', '^', '@', '#'};
        Comparable[] nuts=new Comparable[N];
        Comparable[] bolts=new Comparable[N];

        for(int i=0;i<N;i++){
            nuts[i]=i;
            bolts[i]=N-i-1;
        }

        NutsAndBolts.sort(nuts,bolts);

        for (int j = 0; j < N; j++) {
            assertEquals(0, bolts[j].compareTo(nuts[j]));
        }

    }
}