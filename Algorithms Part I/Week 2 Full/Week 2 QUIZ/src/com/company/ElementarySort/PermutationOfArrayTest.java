package com.company.ElementarySort;

import org.junit.Test;

import static org.junit.Assert.*;

public class PermutationOfArrayTest {

    @Test
    public void permutationforSame() {
        int[] a = {4, 6, 5, 9, 8};
        int[] b = {4, 6, 5, 9, 8};
        assertTrue(PermutationOfArray.Permutation(a, b));
    }

    @Test
    public void permutationforSameButOrderDiffer() {
        int[] a = {5, 6, 9, 8,4};
        int[] b = {4, 6, 5, 9, 8};
        assertTrue(PermutationOfArray.Permutation(a, b));
    }

    @Test
    public void permutationforDiffer() {
        int[] a = {5, 6, 9, 8, 4,9};
        int[] b = {4, 6, 5, 9, 8};
        assertFalse(PermutationOfArray.Permutation(a, b));
    }


}