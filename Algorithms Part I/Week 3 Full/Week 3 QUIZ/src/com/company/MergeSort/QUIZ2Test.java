package com.company.MergeSort;

import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;

import static com.company.MergeSort.QUIZ2.sort;

public class QUIZ2Test {

    @Test
    public void test1() {
        Integer[] arr = {3, 2, 1};
        Assert.assertEquals(reversePairs(arr), sort(arr));
    }

    @Test
    public void test2(){
        Integer[] arr={10,7,8,9,90,4,3,1,2,11,12,8,6,5};
        Assert.assertEquals(reversePairs(arr)+1, sort(arr));  //1 repeat
    }

    @Test
    public void test3(){
        SecureRandom random=new SecureRandom(); //for avoiding duplicates
        int n= random.nextInt(100);
        Integer []a=new Integer[n];
        for(int i=0;i<n;i++){
            int p=random.nextInt();
            a[i]= p;
        }
        Assert.assertEquals(reversePairs(a),sort(a));
    }

    private int reversePairs(Integer[] nums) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i])
                    count++;
            }
        }
        return count;
    }
}