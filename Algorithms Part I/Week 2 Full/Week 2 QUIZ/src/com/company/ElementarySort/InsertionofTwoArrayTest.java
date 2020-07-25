package com.company.ElementarySort;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsertionofTwoArrayTest {

    @Test
    public void testFullElement() {
        int[] a = {1, 2, 3,4,5,6};
        int[] b = {2, 3, 1,5,4,6};
        assertEquals(6, InsertionofTwoArray.function(a, b));
    }

    @Test
    public void testNoElement() {
        int[] a = {3, 2, 1, 0};
        int[] b = {4, 6, 5, 9, 8};
        Assert.assertEquals(0, InsertionofTwoArray.function(a, b));
    }

    @Test
    public void testOneElement() {
        int[] a = {3, 2, 1};
        int[] b = {4, 3, 5, 6, 7};
        Assert.assertEquals(1, InsertionofTwoArray.function(a, b));
    }

    @Test
    public void testMulElement() {
        int[] a = {7, 3};
        int[] b = {4, 3, 5, 6, 7};
        Assert.assertEquals(2, InsertionofTwoArray.function(a, b));
    }
}