package com.company.PriorityQueues;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DynamicMedianTest {

    //for solution 1 & solution 2
    @Test
    public void DynamicTest(){
        List<Integer> expect = new ArrayList<>();
        DynamicMedian<Integer> dynamicMedian = new DynamicMedian<>();
        Random r = new Random();
        int N = 8000;
        int max = 0;
        for (int i = 0; i < N; i++) {
             int val = r.nextInt(N);
             dynamicMedian.insert(val);
             expect.add(val);
             Collections.sort(expect);
             max = Math.max(expect.size(), max);
        }
        Assert.assertEquals(expect.get((expect.size() - 1) / 2).intValue(), dynamicMedian.median());
        expect.remove((expect.size() - 1) / 2);
        System.out.println(dynamicMedian.delMedian());
        System.out.println(max);
    }

    //for solution 2
    @Test
    public void DynamicTest2(){
        List<Integer> expect = new ArrayList<>();
        DynamicMedian<Integer> dynamicMedian = new DynamicMedian<>();
        Random r = new Random();
        int N = 80000;
        int max = 0;
        for (int i = 0; i < N; i++) {
            if (expect.isEmpty() || r.nextInt(100) >= 45) {
                int val = r.nextInt(N);
                dynamicMedian.insert(val);
                expect.add(val);
                Collections.sort(expect);
                max = Math.max(expect.size(), max);
            } else {
                Assert.assertEquals(expect.get((expect.size() - 1) / 2).intValue(), dynamicMedian.median());
                expect.remove((expect.size() - 1) / 2);
                dynamicMedian.delMedian();
            }
        }
        System.out.println(max);
    }
}